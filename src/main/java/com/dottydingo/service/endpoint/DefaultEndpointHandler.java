package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.ContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusBuilder;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class DefaultEndpointHandler<C extends EndpointContext> implements EndpointHandler
{
    private Logger logger = LoggerFactory.getLogger(DefaultEndpointHandler.class);

    private ContextBuilder<C,?,?,?> contextBuilder;
    private ErrorHandler<C> errorHandler;
    private ContextStatusRegistry contextStatusRegistry;
    private ContextStatusBuilder<ContextStatus,C> contextStatusBuilder;
    private PipelineInitiator<C> pipelineInitiator;

    public void setContextBuilder(ContextBuilder<C,?,?,?> contextBuilder)
    {
        this.contextBuilder = contextBuilder;
    }

    public void setErrorHandler(ErrorHandler<C> errorHandler)
    {
        this.errorHandler = errorHandler;
    }

    public void setContextStatusRegistry(ContextStatusRegistry contextStatusRegistry)
    {
        this.contextStatusRegistry = contextStatusRegistry;
    }

    public void setContextStatusBuilder(ContextStatusBuilder<ContextStatus, C> contextStatusBuilder)
    {
        this.contextStatusBuilder = contextStatusBuilder;
    }

    public void setPipelineInitiator(PipelineInitiator<C> pipelineInitiator)
    {
        this.pipelineInitiator = pipelineInitiator;
    }

    public void handleRequest(HttpServletRequest request,HttpServletResponse response)
    {
        C context = null;
        try
        {
            context = contextBuilder.buildContext(request,response);
        }
        catch (Throwable t)
        {
            context = contextBuilder.buildErrorContext(request,response);
            errorHandler.handleError(context,t);
            return;
        }

        try
        {
            ContextStatus status = contextStatusBuilder.buildContextStatus(context);
            context.setContextStatus(status);
            contextStatusRegistry.registerContext(context.getRequestId(),status);
            pipelineInitiator.initiate(context);
        }
        catch (Exception e)
        {
            errorHandler.handleError(context,e);
        }

        logger.debug("Handler complete.");
    }
}

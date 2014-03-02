package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.AbstractContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.context.UserContext;
import com.dottydingo.service.endpoint.context.UserContextBuilder;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusBuilder;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.endpoint.status.EndpointStatus;
import com.dottydingo.service.pipeline.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class DefaultEndpointHandler<C extends EndpointContext,U extends UserContext> implements EndpointHandler
{
    private Logger logger = LoggerFactory.getLogger(DefaultEndpointHandler.class);

    private AbstractContextBuilder<C,?,?> contextBuilder;
    private ErrorHandler<C> errorHandler;
    private ContextStatusRegistry contextStatusRegistry;
    private ContextStatusBuilder<ContextStatus,C> contextStatusBuilder;
    private PipelineInitiator<C> pipelineInitiator;
    private UserContextBuilder<U,C> userContextBuilder;
    private EndpointStatus endpointStatus;

    public void setContextBuilder(AbstractContextBuilder<C,?,?> contextBuilder)
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

    public void setUserContextBuilder(UserContextBuilder<U, C> userContextBuilder)
    {
        this.userContextBuilder = userContextBuilder;
    }

    public void setEndpointStatus(EndpointStatus endpointStatus)
    {
        this.endpointStatus = endpointStatus;
    }

    public void handleRequest(HttpServletRequest request,HttpServletResponse response)
    {
        endpointStatus.startRequest();

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
            context.setUserContext(userContextBuilder.buildUserContext(context));
            contextStatusRegistry.registerContext(context.getRequestId(),contextStatusBuilder.buildContextStatus(context));
            pipelineInitiator.initiate(context);
        }
        catch (Exception e)
        {
            errorHandler.handleError(context,e);
        }

        logger.debug("Handler complete.");
    }
}

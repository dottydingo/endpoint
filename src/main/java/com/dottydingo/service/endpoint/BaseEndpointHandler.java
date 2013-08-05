package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.ContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusBuilder;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.ErrorHandler;
import com.dottydingo.service.pipeline.PhaseSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class BaseEndpointHandler<C extends EndpointContext> implements EndpointHandler
{
    private Logger logger = LoggerFactory.getLogger(BaseEndpointHandler.class);

    private ContextBuilder<C,?,?,?> contextBuilder;
    private PhaseSelector<C> initialPhaseSelector;
    private ErrorHandler<C> errorHandler;
    private ContextStatusRegistry contextStatusRegistry;
    private ContextStatusBuilder<ContextStatus,C> contextStatusBuilder;

    public void setContextBuilder(ContextBuilder<C,?,?,?> contextBuilder)
    {
        this.contextBuilder = contextBuilder;
    }

    public void setInitialPhaseSelector(PhaseSelector<C> initialPhaseSelector)
    {
        this.initialPhaseSelector = initialPhaseSelector;
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

    public void handleRequest(HttpServletRequest request,HttpServletResponse response)
    {
        C context = null;
        try
        {
            context = contextBuilder.buildContext(request,response);
        }
        catch (Throwable t)
        {
            errorHandler.handleError(context,t);
            return;
        }

        try
        {
            ContextStatus status = contextStatusBuilder.buildContextStatus(context);
            context.setContextStatus(status);
            contextStatusRegistry.registerContext(context.getRequestId(),status);
            beforePipeline(context);
            initialPhaseSelector.getNextPhase(context).execute(context);
            afterPipeline(context);
        }
        catch (Exception e)
        {
            errorHandler.handleError(context,e);
        }

        logger.debug("Pipeline complete.");
    }

    protected void beforePipeline(C context){}
    protected void afterPipeline(C context){}
}

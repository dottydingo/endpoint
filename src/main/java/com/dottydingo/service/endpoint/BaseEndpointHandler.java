package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.ContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.ErrorHandler;
import com.dottydingo.service.pipeline.PhaseSelector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class BaseEndpointHandler<C extends EndpointContext<?,?,?>> implements EndpointHandler
{
    private ContextBuilder<C,?,?,?> contextBuilder;
    private PhaseSelector<C> initialPhaseSelector;
    private ErrorHandler<C> errorHandler;
    private ContextStatusRegistry contextStatusRegistry;

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
            contextStatusRegistry.registerContext(context.getRequestId(),context.getContextStatus());
            beforePipeline(context);
            initialPhaseSelector.getNextPhase(context).execute(context);
            afterPipeline(context);
        }
        catch (Exception e)
        {
            errorHandler.handleError(context,e);
        }
    }

    protected void beforePipeline(C context){}
    protected void afterPipeline(C context){}
}

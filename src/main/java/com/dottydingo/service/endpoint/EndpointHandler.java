package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.ErrorHandler;
import com.dottydingo.service.pipeline.PhaseSelector;
import com.dottydingo.service.endpoint.context.ContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.context.EndpointRequest;
import com.dottydingo.service.endpoint.context.EndpointResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public class EndpointHandler<C extends EndpointContext<REQ,RES,STAT>,REQ extends EndpointRequest,RES extends EndpointResponse,
        STAT extends ContextStatus>
{
    private ContextBuilder<C,REQ,RES,STAT> contextBuilder;
    private PhaseSelector<C> initialPhaseSelector;
    private ErrorHandler<C> errorHandler;
    private ContextStatusRegistry<STAT> contextStatusRegistry;

    public void setContextBuilder(ContextBuilder<C, REQ, RES,STAT> contextBuilder)
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

    public void setContextStatusRegistry(ContextStatusRegistry<STAT> contextStatusRegistry)
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
            initialPhaseSelector.getNextPhase(context).execute(context);
        }
        catch (Exception e)
        {
            errorHandler.handleError(context,e);
        }
    }
}

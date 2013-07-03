package com.dottydingo.service.endpoint;

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
public class EndpointHandler<C extends EndpointContext<REQ,RES>,REQ extends EndpointRequest,RES extends EndpointResponse>
{
    private ContextBuilder<C,REQ,RES> contextBuilder;
    private PhaseSelector<C> initialPhaseSelector;
    private ErrorHandler<C> errorHandler;

    public void setContextBuilder(ContextBuilder<C, REQ, RES> contextBuilder)
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

    public void handleRequest(HttpServletRequest request,HttpServletResponse response)
    {
        C context = null;
        try
        {
            context = contextBuilder.buildContext(request,response);
            initialPhaseSelector.getNextPhase(context).execute(context);
        }
        catch (Exception e)
        {
            if(context == null)
                context = contextBuilder.buildErrorContext(request,response);

            errorHandler.handleError(context,e);
        }
    }
}

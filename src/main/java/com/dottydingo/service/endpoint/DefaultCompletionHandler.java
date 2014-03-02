package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.endpoint.status.EndpointStatus;
import com.dottydingo.service.tracelog.Trace;
import com.dottydingo.service.tracelog.TraceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DefaultCompletionHandler<C extends EndpointContext> implements CompletionHandler<C>
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private TraceManager traceManager;
    private ContextStatusRegistry contextStatusRegistry;
    private RequestLogHandler<C> requestLogHandler;
    private List<CompletionCallback<C>> completionCallbacks = new ArrayList<CompletionCallback<C>>();
    private EndpointStatus endpointStatus;

    public void setTraceManager(TraceManager traceManager)
    {
        this.traceManager = traceManager;
    }

    public void setContextStatusRegistry(ContextStatusRegistry contextStatusRegistry)
    {
        this.contextStatusRegistry = contextStatusRegistry;
    }

    public void setRequestLogHandler(RequestLogHandler<C> requestLogHandler)
    {
        this.requestLogHandler = requestLogHandler;
    }

    public void setCompletionCallbacks(List<CompletionCallback<C>> completionCallbacks)
    {
        this.completionCallbacks = completionCallbacks;
    }

    public void setEndpointStatus(EndpointStatus endpointStatus)
    {
        this.endpointStatus = endpointStatus;
    }

    @Override
    public void completeRequest(C endpointContext)
    {
        MDC.put("CID", endpointContext.getCorrelationId());

        Trace trace = endpointContext.getTrace();
        if(trace!= null)
            traceManager.associateTrace(trace);

        ContextStatus contextStatus = contextStatusRegistry.getContextStatus(endpointContext.getRequestId());
        if(contextStatus != null)
            contextStatusRegistry.associateContextStatus(contextStatus);

        try
        {
            for (CompletionCallback<C> callback : completionCallbacks)
            {
                callback.onComplete(endpointContext);
            }
        }
        catch (Throwable throwable)
        {
            logger.error("Error completing request.", throwable);
        }

        if (trace != null)
        {
            traceManager.disassociateTrace();

            try
            {
                trace.close();
            }
            catch (Throwable t)
            {
                logger.error("Error closing trace.", t);
            }
        }

        if(contextStatus != null)
            contextStatusRegistry.disassociateContextStatus();
        contextStatusRegistry.unRegisterContext(endpointContext.getRequestId());

        if(requestLogHandler != null)
            requestLogHandler.logRequest(endpointContext);

        endpointStatus.endRequest();

        EndpointAsyncContext endpointAsyncContext = endpointContext.getEndpointAsyncContext();
        if(endpointAsyncContext != null)
            endpointAsyncContext.complete();


    }
}

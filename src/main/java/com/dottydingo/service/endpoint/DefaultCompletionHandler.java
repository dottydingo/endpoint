package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusManager;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.tracelog.Trace;
import com.dottydingo.service.tracelog.TraceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 */
public class DefaultCompletionHandler<C extends EndpointContext> implements CompletionHandler<C>
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private TraceManager traceManager;
    private ContextStatusManager contextStatusManager;
    private ContextStatusRegistry contextStatusRegistry;
    private RequestLogHandler<C> requestLogHandler;

    public void setTraceManager(TraceManager traceManager)
    {
        this.traceManager = traceManager;
    }

    public void setContextStatusManager(ContextStatusManager contextStatusManager)
    {
        this.contextStatusManager = contextStatusManager;
    }

    public void setContextStatusRegistry(ContextStatusRegistry contextStatusRegistry)
    {
        this.contextStatusRegistry = contextStatusRegistry;
    }

    public void setRequestLogHandler(RequestLogHandler<C> requestLogHandler)
    {
        this.requestLogHandler = requestLogHandler;
    }

    @Override
    public void completeRequest(C endpointContext)
    {
        MDC.put("CID", endpointContext.getCorrelationId());

        Trace trace = endpointContext.getTrace();
        if(trace!= null)
            traceManager.associateTrace(trace);

        ContextStatus contextStatus = endpointContext.getContextStatus();
        if(contextStatus != null)
            contextStatusManager.associateContextStatus(contextStatus);

        endpointContext.requestComplete();
        try
        {
            finalizeResponse(endpointContext);
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

        if (contextStatus != null)
        {
            contextStatusManager.disassociateContextStatus();
        }

        contextStatusRegistry.unRegisterContext(endpointContext.getRequestId());

        if(requestLogHandler != null)
            requestLogHandler.logRequest(endpointContext);

    }

    protected void finalizeResponse(C phaseContext) throws Exception
    {
    }
}
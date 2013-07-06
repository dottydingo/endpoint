package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusManager;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.Phase;
import com.dottydingo.service.endpoint.configuration.EndpointConfiguration;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.tracelog.Trace;
import com.dottydingo.service.tracelog.TraceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 */
public abstract class FinalizeResponsePhase<C extends EndpointContext> implements Phase<C>
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private TraceManager traceManager;
    private ContextStatusManager contextStatusManager;
    private ContextStatusRegistry contextStatusRegistry;
    private String name;

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

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public void execute(C phaseContext) throws Exception
    {

        MDC.put("CID",phaseContext.getCorrelationId());

        Trace trace = phaseContext.getTrace();
        if(trace!= null)
            traceManager.startTrace(trace);

        ContextStatus contextStatus = phaseContext.getContextStatus();
        if(contextStatus != null)
            contextStatusManager.associateContextStatus(contextStatus);

        try
        {
            logger.debug("Starting phase {}",name);

            finalizeResponse(phaseContext);
        }
        catch (Throwable throwable)
        {
            logger.error(String.format("Error finalizing response in phase %s",name),throwable);
        }

        if (trace != null)
        {
            traceManager.endTrace();

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
            contextStatusManager.unAssociateContextStatus();

        contextStatusRegistry.unRegisterContext(phaseContext.getRequestId());

        MDC.clear();
        phaseContext.requestComplete();
    }

    protected void finalizeResponse(C phaseContext) throws Exception {}
}

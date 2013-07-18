package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusManager;
import com.dottydingo.service.pipeline.Phase;
import com.dottydingo.service.tracelog.Trace;
import com.dottydingo.service.tracelog.TraceManager;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 */
public abstract class AbstractEndpointPhase<C extends EndpointContext> implements Phase<C>
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private TraceManager traceManager;
    private ContextStatusManager contextStatusManager;
    private String name;

    public void setTraceManager(TraceManager traceManager)
    {
        this.traceManager = traceManager;
    }

    public void setContextStatusManager(ContextStatusManager contextStatusManager)
    {
        this.contextStatusManager = contextStatusManager;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void execute(C phaseContext) throws Exception
    {
        if(phaseContext.isTimedOut())
            throw new EndpointTimeoutException(String.format("Endpoint request timed out."));

        MDC.put("CID",phaseContext.getCorrelationId());

        Trace trace = phaseContext.getTrace();
        if(trace!= null)
            traceManager.associateTrace(trace);

        ContextStatus contextStatus = phaseContext.getContextStatus();
        if(contextStatus != null)
            contextStatusManager.associateContextStatus(contextStatus);

        try
        {
            logger.debug("Starting phase {}",name);
            phaseContext.getContextStatus().startTimer(String.format("phase:%s",name));

            executePhase(phaseContext);

            logger.debug("Completing phase {}",name);
        }
        finally
        {
            phaseContext.getContextStatus().stopTimer(String.format("phase:%s",name));
            MDC.clear();
            if(trace != null)
                traceManager.disassociateTrace();
            if(contextStatus != null)
                contextStatusManager.disassociateContextStatus();
        }


    }

    protected abstract void executePhase(C phaseContext) throws Exception;
}

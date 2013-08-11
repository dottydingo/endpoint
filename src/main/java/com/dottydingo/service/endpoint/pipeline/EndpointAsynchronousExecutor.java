package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.AsynchronousPhaseExecutor;

/**
 */
public class EndpointAsynchronousExecutor<C extends EndpointContext> extends AsynchronousPhaseExecutor<C>
{
    private ContextStatusRegistry contextStatusRegistry;

    public void setContextStatusRegistry(ContextStatusRegistry contextStatusRegistry)
    {
        this.contextStatusRegistry = contextStatusRegistry;
    }

    @Override
    public void execute(C phaseContext)
    {
        ContextStatus status = contextStatusRegistry.getContextStatus(phaseContext.getRequestId());
        if(status != null)
            status.startTimer(String.format("phase:%s - queued",phase.getName()));
        super.execute(phaseContext);
    }

    @Override
    public void run(C phaseContext)
    {
        ContextStatus status = contextStatusRegistry.getContextStatus(phaseContext.getRequestId());
        if(status != null)
            status.stopTimer(String.format("phase:%s - queued",phase.getName()));
        super.run(phaseContext);
    }
}

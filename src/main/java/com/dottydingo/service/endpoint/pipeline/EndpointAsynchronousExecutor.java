package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.pipeline.AsynchronousPhaseExecutor;

/**
 */
public class EndpointAsynchronousExecutor<C extends EndpointContext> extends AsynchronousPhaseExecutor<C>
{
    @Override
    public void execute(C phaseContext)
    {
        phaseContext.getContextStatus().startTimer(String.format("phase:%s - queued",phase.getName()));
        super.execute(phaseContext);
    }

    @Override
    public void run(C phaseContext)
    {
        phaseContext.getContextStatus().stopTimer(String.format("phase:%s - queued",phase.getName()));
        super.run(phaseContext);
    }
}

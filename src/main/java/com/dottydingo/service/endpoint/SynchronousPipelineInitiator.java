package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.pipeline.PhaseSelector;

/**
 */
public class SynchronousPipelineInitiator<C extends EndpointContext> implements PipelineInitiator<C>
{
    private PhaseSelector<C> initialPhaseSelector;

    public void setInitialPhaseSelector(PhaseSelector<C> initialPhaseSelector)
    {
        this.initialPhaseSelector = initialPhaseSelector;
    }

    @Override
    public void initiate(C context)
    {
        initialPhaseSelector.getNextPhase(context).execute(context);
    }
}

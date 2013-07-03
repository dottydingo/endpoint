package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.pipeline.Phase;
import com.dottydingo.service.endpoint.configuration.EndpointConfiguration;
import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public class FinalizeResponsePhase<C extends EndpointContext,CONFIG extends EndpointConfiguration> implements Phase<C>
{
    @Override
    public void execute(C phaseContext) throws Exception
    {

    }
}

package com.dottydingo.service.endpoint.pipeline;

import com.dottydingo.service.pipeline.Phase;
import com.dottydingo.service.endpoint.configuration.EndpointConfiguration;
import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public class PrepareRequestPhase<C extends EndpointContext,CONFIG extends EndpointConfiguration> implements Phase<C>
{
    private CONFIG configuration;

    public void setConfiguration(CONFIG configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public void execute(C phaseContext) throws Exception
    {

    }


}

package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public interface PipelineInitiator<C extends EndpointContext>
{
    void initiate(C context);
}

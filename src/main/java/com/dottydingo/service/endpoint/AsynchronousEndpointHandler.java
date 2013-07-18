package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public class AsynchronousEndpointHandler<C extends EndpointContext<?,?,?>> extends BaseEndpointHandler<C>
{

    @Override
    protected void beforePipeline(C context)
    {
        context.getEndpointRequest().getHttpServletRequest().startAsync();
    }
}

package com.dottydingo.service.endpoint.status;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public class DefaultContextStatusBuilder implements ContextStatusBuilder<ContextStatus,EndpointContext>
{
    @Override
    public ContextStatus buildContextStatus(EndpointContext endpointContext)
    {
        return new DefaultContextStatus<EndpointContext>(endpointContext);
    }
}

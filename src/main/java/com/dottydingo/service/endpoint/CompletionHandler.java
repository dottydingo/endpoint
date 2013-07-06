package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public interface CompletionHandler<C extends EndpointContext>
{
    void completeRequest(C endpointContext);
}

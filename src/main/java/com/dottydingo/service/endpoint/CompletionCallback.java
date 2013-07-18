package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public interface CompletionCallback<C extends EndpointContext>
{
    void onComplete(C context);
}

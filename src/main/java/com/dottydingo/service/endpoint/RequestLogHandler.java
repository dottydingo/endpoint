package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public interface RequestLogHandler<C extends EndpointContext>
{
    void logRequest(C context);
}

package com.dottydingo.service.endpoint.context;

/**
 */
public interface RequestCorrelationIdGenerator<C extends EndpointContext>
{
    String generateRequestCorrelationId(C endpointContext);
}

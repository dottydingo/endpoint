package com.dottydingo.service.endpoint.pipeline;

/**
 */
public class EndpointTimeoutException extends RuntimeException
{
    public EndpointTimeoutException(String message)
    {
        super(message);
    }
}

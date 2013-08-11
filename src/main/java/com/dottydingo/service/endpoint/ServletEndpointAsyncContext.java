package com.dottydingo.service.endpoint;

/**
 */
public class ServletEndpointAsyncContext implements EndpointAsyncContext
{
    private javax.servlet.AsyncContext asyncContext;

    public ServletEndpointAsyncContext(javax.servlet.AsyncContext asyncContext)
    {
        this.asyncContext = asyncContext;
    }

    @Override
    public void complete()
    {
        asyncContext.complete();
    }
}

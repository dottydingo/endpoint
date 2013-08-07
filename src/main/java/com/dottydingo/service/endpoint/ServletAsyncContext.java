package com.dottydingo.service.endpoint;

/**
 */
public class ServletAsyncContext implements AsyncContext
{
    private javax.servlet.AsyncContext asyncContext;

    public ServletAsyncContext(javax.servlet.AsyncContext asyncContext)
    {
        this.asyncContext = asyncContext;
    }

    @Override
    public void complete()
    {
        asyncContext.complete();
    }
}

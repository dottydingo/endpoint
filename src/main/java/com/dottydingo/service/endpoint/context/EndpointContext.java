package com.dottydingo.service.endpoint.context;

import com.dottydingo.service.endpoint.AsyncContext;
import com.dottydingo.service.endpoint.CompletionHandler;
import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.tracelog.Trace;

/**
 */
public class EndpointContext<REQ extends EndpointRequest,RES extends EndpointResponse,S extends ContextStatus,
        U extends UserContext>
{
    protected Long requestId;
    protected REQ endpointRequest;
    protected RES endpointResponse;
    protected String correlationId;
    protected Trace trace;
    protected Throwable error;
    protected long startTimestamp = System.currentTimeMillis();
    protected long endTimestamp = -1;
    protected volatile boolean timedOut;
    protected volatile boolean complete;
    protected CompletionHandler completionHandler;
    protected U userContext;
    protected S contextStatus;
    private AsyncContext asyncContext;

    public void setCompletionHandler(CompletionHandler completionHandler)
    {
        this.completionHandler = completionHandler;
    }

    public Long getRequestId()
    {
        return requestId;
    }

    public void setRequestId(Long requestId)
    {
        this.requestId = requestId;
    }

    public REQ getEndpointRequest()
    {
        return endpointRequest;
    }

    public void setEndpointRequest(REQ endpointRequest)
    {
        this.endpointRequest = endpointRequest;
    }

    public RES getEndpointResponse()
    {
        return endpointResponse;
    }

    public void setEndpointResponse(RES endpointResponse)
    {
        this.endpointResponse = endpointResponse;
    }

    public String getCorrelationId()
    {
        return correlationId;
    }

    public void setCorrelationId(String correlationId)
    {
        this.correlationId = correlationId;
    }

    public Trace getTrace()
    {
        return trace;
    }

    public void setTrace(Trace trace)
    {
        this.trace = trace;
    }

    public Throwable getError()
    {
        return error;
    }

    public void setError(Throwable error)
    {
        this.error = error;
    }

    public U getUserContext()
    {
        return userContext;
    }

    public void setUserContext(U userContext)
    {
        this.userContext = userContext;
    }

    public S getContextStatus()
    {
        return contextStatus;
    }

    public void setContextStatus(S contextStatus)
    {
        this.contextStatus = contextStatus;
    }

    public void requestComplete()
    {
        endTimestamp = System.currentTimeMillis();
        complete = true;
        if(completionHandler != null)
            completionHandler.completeRequest(this);
    }

    public boolean isComplete()
    {
        return complete;
    }

    public long getStartTimestamp()
    {
        return startTimestamp;
    }

    public long getElapsedTime()
    {
        long end = endTimestamp;
        if(end == -1)
            end = System.currentTimeMillis();

        return end - startTimestamp;
    }

    public void setTimedOut()
    {
        timedOut = true;
    }

    public boolean isTimedOut()
    {
        return timedOut;
    }

    public AsyncContext getAsyncContext()
    {
        return asyncContext;
    }

    public void setAsyncContext(AsyncContext asyncContext)
    {
        this.asyncContext = asyncContext;
    }
}

package com.dottydingo.service.endpoint.context;

import com.dottydingo.service.tracelog.Trace;

/**
 */
public class EndpointContext<REQ extends EndpointRequest,RES extends EndpointResponse>
{
    protected REQ endpointRequest;
    protected RES endpointResponse;
    protected String correlationId;
    protected Trace trace;
    protected Throwable error;

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
}

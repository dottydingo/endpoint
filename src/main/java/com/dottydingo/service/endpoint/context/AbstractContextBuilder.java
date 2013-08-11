package com.dottydingo.service.endpoint.context;

import com.dottydingo.service.endpoint.CompletionHandler;
import com.dottydingo.service.endpoint.configuration.EndpointConfiguration;
import com.dottydingo.service.endpoint.io.BufferingInputStreamWrapper;
import com.dottydingo.service.endpoint.io.SizeTrackingOutputStream;
import com.dottydingo.service.tracelog.Trace;
import com.dottydingo.service.tracelog.TraceFactory;
import com.dottydingo.service.tracelog.TraceType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
public abstract class AbstractContextBuilder<C extends EndpointContext,REQ extends EndpointRequest,RES extends EndpointResponse>
{
    private AtomicLong counter = new AtomicLong(0);
    protected EndpointConfiguration endpointConfiguration;
    protected TraceFactory traceFactory;
    protected CompletionHandler<C> completionHandler;

    public void setEndpointConfiguration(EndpointConfiguration endpointConfiguration)
    {
        this.endpointConfiguration = endpointConfiguration;
    }

    public void setTraceFactory(TraceFactory traceFactory)
    {
        this.traceFactory = traceFactory;
    }

    public void setCompletionHandler(CompletionHandler<C> completionHandler)
    {
        this.completionHandler = completionHandler;
    }

    public C buildContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException
    {
        C context = createContextInstance();
        context.setRequestId(counter.incrementAndGet());

        REQ request = createRequest(httpServletRequest);
        request.setInputStream(wrapInputStream(httpServletRequest.getInputStream()));

        RES response = createResponse(httpServletResponse);
        response.setOutputStream(wrapOutputStream(httpServletResponse.getOutputStream()));

        context.setEndpointRequest(request);
        context.setEndpointResponse(response);

        // set or assign a correlation ID
        context.setCorrelationId(getCorrelationId(request));

        // set the correlation ID on the response
        response.setHeader(endpointConfiguration.getCorrelationIdHeaderName(),context.getCorrelationId());

        // create a trace if requested
        context.setTrace(getTrace(request,context.getCorrelationId()));

        context.setCompletionHandler(completionHandler);

        return context;
    }

    public C buildErrorContext(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)

    {
        C context = createContextInstance();
        context.setRequestId(counter.incrementAndGet());

        REQ request = createRequest(httpServletRequest);
        RES response = createResponse(httpServletResponse);

        context.setEndpointRequest(request);
        context.setEndpointResponse(response);

        // set or assign a correlation ID
        context.setCorrelationId(getCorrelationId(request));

        // create a trace if requested
        context.setTrace(getTrace(request,context.getCorrelationId()));

        return context;
    }

    protected abstract C createContextInstance();

    protected abstract RES createResponseInstance();

    protected abstract REQ createRequestInstance();

    protected RES createResponse(HttpServletResponse httpServletResponse)
    {
        RES response = createResponseInstance();
        response.setHttpServletResponse(httpServletResponse);


        return (RES) response;
    }

    protected REQ createRequest(HttpServletRequest httpServletRequest)
    {
        REQ request = createRequestInstance();
        request.setHttpServletRequest(httpServletRequest);

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements())
        {
            String name = headerNames.nextElement();
            request.addHeader(name,httpServletRequest.getHeader(name));
        }

        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String name = parameterNames.nextElement();
            request.addParameter(name, httpServletRequest.getParameter(name));
        }

        request.setRequestMethod(httpServletRequest.getMethod());
        request.setContentType(httpServletRequest.getContentType());

        request.setRequestUrl(httpServletRequest.getRequestURL().toString());
        request.setQueryString(httpServletRequest.getQueryString());
        request.setRequestUri(httpServletRequest.getRequestURI());
        request.setBaseUrl(getBaseUrl(httpServletRequest));

        request.setAuthType(httpServletRequest.getAuthType());
        request.setRemoteAddress(httpServletRequest.getRemoteAddr());
        request.setServerName(httpServletRequest.getServerName());


        return request;
    }

    protected InputStream wrapInputStream(InputStream inputStream)
    {
        return new BufferingInputStreamWrapper(inputStream,endpointConfiguration.getMaxRequestBodySize(),true);
    }

    protected OutputStream wrapOutputStream(OutputStream outputStream)
    {
        return new SizeTrackingOutputStream(outputStream,true);
    }

    protected String getCorrelationId(REQ request)
    {
        String correlationId = request.getFirstHeader(endpointConfiguration.getCorrelationIdParameterName());

        if(correlationId == null || correlationId.length()==0)
            correlationId = request.getFirstHeader(endpointConfiguration.getCorrelationIdHeaderName());

        if(correlationId == null || correlationId.length()==0)
            correlationId = UUID.randomUUID().toString();

        return correlationId;
    }

    protected Trace getTrace(REQ request,String correlationId)
    {
        if(!endpointConfiguration.isAllowTrace())
            return null;

        String traceString = request.getFirstParameter(endpointConfiguration.getTraceParameterName());

        if(traceString == null || traceString.length() ==0)
            traceString = request.getFirstHeader(endpointConfiguration.getTraceHeaderName());

        if(traceString == null || traceString.length() == 0)
            return null;

        String[] split = traceString.split(":");

        TraceType traceType = null;
        try
        {
            traceType = TraceType.valueOf(split[0]);
        }
        catch (IllegalArgumentException e)
        {
            return null; // todo should this throw an exception?
        }

        switch (traceType)
        {
            case email:
            {
                if(split.length != 2)
                    return null;  // todo should this throw an exception?

                Set<String> restrictedTraceDomains = endpointConfiguration.getRestrictedTraceDomains();
                if(restrictedTraceDomains != null && restrictedTraceDomains.size() > 0)
                {
                    if(!restrictedTraceDomains.contains(split[1]))
                        return null; // not allowed. todo should this throw an exception?
                }
                return traceFactory.createTrace(traceType,split[1]);
            }
            case file:
                return traceFactory.createTrace(traceType,correlationId);

        }

        return null;
    }


    protected String getBaseUrl(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        String requestUri = request.getRequestURI();

        int pos = url.indexOf(requestUri);
        if(pos < 0)
            return url.toString();

        return url.substring(0,pos + request.getContextPath().length());
    }
}

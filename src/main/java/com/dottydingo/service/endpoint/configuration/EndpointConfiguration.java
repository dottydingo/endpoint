package com.dottydingo.service.endpoint.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 */
public class EndpointConfiguration
{
    private String traceParameterName = "trace";
    private String traceHeaderName = "X-Trace";
    private String correlationIdParameterName = "cid";
    private String correlationIdHeaderName = "X-Correlation-ID";

    private boolean allowTrace = true;
    private Set<String> restrictedTraceDomains = new HashSet<String>();
    private boolean forceConnectionClose;

    private int maxRequestBodySize = 1024*1024; // 1 megabyte
    private boolean enableResponseCompression = false;
    private int responseCompressionThreshold = 1024;

    public String getTraceParameterName()
    {
        return traceParameterName;
    }

    public void setTraceParameterName(String traceParameterName)
    {
        this.traceParameterName = traceParameterName;
    }

    public String getTraceHeaderName()
    {
        return traceHeaderName;
    }

    public void setTraceHeaderName(String traceHeaderName)
    {
        this.traceHeaderName = traceHeaderName;
    }

    public String getCorrelationIdParameterName()
    {
        return correlationIdParameterName;
    }

    public void setCorrelationIdParameterName(String correlationIdParameterName)
    {
        this.correlationIdParameterName = correlationIdParameterName;
    }

    public String getCorrelationIdHeaderName()
    {
        return correlationIdHeaderName;
    }

    public void setCorrelationIdHeaderName(String correlationIdHeaderName)
    {
        this.correlationIdHeaderName = correlationIdHeaderName;
    }

    public boolean isAllowTrace()
    {
        return allowTrace;
    }

    public void setAllowTrace(boolean allowTrace)
    {
        this.allowTrace = allowTrace;
    }

    public int getMaxRequestBodySize()
    {
        return maxRequestBodySize;
    }

    public void setMaxRequestBodySize(int maxRequestBodySize)
    {
        this.maxRequestBodySize = maxRequestBodySize;
    }

    public Set<String> getRestrictedTraceDomains()
    {
        return restrictedTraceDomains;
    }

    public void setRestrictedTraceDomains(Set<String> restrictedTraceDomains)
    {
        this.restrictedTraceDomains = restrictedTraceDomains;
    }

    public void setRestrictedTraceDomainsAsArray(String[] restrictedTraceDomainArray)
    {
        if(restrictedTraceDomainArray != null && restrictedTraceDomainArray.length > 0)
        {
            this.restrictedTraceDomains = new HashSet<String>();
            Collections.addAll(this.restrictedTraceDomains,restrictedTraceDomainArray);
        }
    }

    public boolean getForceConnectionClose()
    {
        return forceConnectionClose;
    }

    public void setForceConnectionClose(boolean forceConnectionClose)
    {
        this.forceConnectionClose = forceConnectionClose;
    }

    public boolean getEnableResponseCompression()
    {
        return enableResponseCompression;
    }

    public void setEnableResponseCompression(boolean enableResponseCompression)
    {
        this.enableResponseCompression = enableResponseCompression;
    }

    public int getResponseCompressionThreshold()
    {
        return responseCompressionThreshold;
    }

    public void setResponseCompressionThreshold(int responseCompressionThreshold)
    {
        this.responseCompressionThreshold = responseCompressionThreshold;
    }
}

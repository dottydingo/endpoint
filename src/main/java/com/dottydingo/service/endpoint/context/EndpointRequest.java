package com.dottydingo.service.endpoint.context;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 */
public class EndpointRequest
{
    protected MultiMap parameters = new MultiMap();
    protected MultiMap headers = new MultiMap();

    private String requestUrl;
    private String baseUrl;
    private String requestUri;
    private String queryString;

    private String contentType;
    private String requestMethod;
    private String authType;
    private String remoteAddress;
    private String serverName;

    private InputStream inputStream;

    private HttpServletRequest httpServletRequest;

    public String getRequestUrl()
    {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getRequestUri()
    {
        return requestUri;
    }

    public void setRequestUri(String requestUri)
    {
        this.requestUri = requestUri;
    }

    public String getQueryString()
    {
        return queryString;
    }

    public void setQueryString(String queryString)
    {
        this.queryString = queryString;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public String getAuthType()
    {
        return authType;
    }

    public void setAuthType(String authType)
    {
        this.authType = authType;
    }

    public String getRemoteAddress()
    {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress)
    {
        this.remoteAddress = remoteAddress;
    }

    public String getServerName()
    {
        return serverName;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }

    public HttpServletRequest getHttpServletRequest()
    {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest)
    {
        this.httpServletRequest = httpServletRequest;
    }

    public void addHeader(String name,String value)
    {
        headers.add(name,value);
    }

    public String getFirstHeader(String name)
    {
        return headers.getFirst(name);
    }

    public List<String> getHeader(String name)
    {
        return headers.get(name);
    }

    public Set<String> getHeaderNames()
    {
        return headers.getKeys();
    }

    public void addParameter(String name,String value)
    {
        parameters.add(name,value);
    }

    public String getFirstParameter(String name)
    {
        return parameters.getFirst(name);
    }

    public List<String> getParameter(String name)
    {
        return parameters.get(name);
    }

    public Set<String> getParameterNames()
    {
        return parameters.getKeys();
    }

    public InputStream getInputStream()
    {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }
}

package com.dottydingo.service.endpoint.context;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 */
public class EndpointResponse
{
    private int responseCode;

    private OutputStream outputStream;
    private HttpServletResponse httpServletResponse;
    private String contentType;
    private String contentEncoding;

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
        if(httpServletResponse != null)
            httpServletResponse.setStatus(responseCode);
    }

    public OutputStream getOutputStream()
    {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream)
    {
        this.outputStream = outputStream;
    }

    public HttpServletResponse getHttpServletResponse()
    {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse)
    {
        this.httpServletResponse = httpServletResponse;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
        if(httpServletResponse != null)
            httpServletResponse.setContentType(contentType);
    }

    public String getContentEncoding()
    {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding)
    {
        this.contentEncoding = contentEncoding;
        if(httpServletResponse != null)
            httpServletResponse.setCharacterEncoding(contentEncoding);
    }

    public void addHeader(String name,String value)
    {
        if(httpServletResponse != null)
            httpServletResponse.addHeader(name,value);
    }

    public void setHeader(String name,String value)
    {
        if(httpServletResponse != null)
            httpServletResponse.setHeader(name,value);
    }
}

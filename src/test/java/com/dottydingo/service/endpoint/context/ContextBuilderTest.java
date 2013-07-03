package com.dottydingo.service.endpoint.context;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

/**
 */
public class ContextBuilderTest
{
    private ContextBuilder contextBuilder = new ContextBuilder();

    @Test
    public void testGetRequestUri()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/foo");
        request.setRequestURI("/foo/bar/baz");

        Assert.assertEquals("/bar/baz",contextBuilder.getRequestUri(request));
    }

    @Test
    public void testGetBaseUrl()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setProtocol("http");
        request.setServerName("www.foobar.com");
        request.setServletPath("/bar");
        request.setContextPath("/foo");
        request.setRequestURI("/foo/bar/baz");

        Assert.assertEquals("http://www.foobar.com:80/foo/bar",contextBuilder.getBaseUrl(request));
    }

    @Test
    public void testCreateRequest()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setProtocol("http");
        request.setServerName("www.foobar.com");
        request.setServletPath("/bar");
        request.setContextPath("/foo");
        request.setQueryString("foo=bar");
        request.setRequestURI("/foo/bar/baz");
        request.addParameter("foo","bar");
        request.addParameter("fizz","buzz");
        request.addHeader("bim","bam");
        request.addHeader("boom","bum");
        request.setContentType("application/json");
        request.setMethod("GET");

        EndpointRequest endpointRequest = contextBuilder.createRequest(request);
        Assert.assertNotNull(endpointRequest);

        Assert.assertSame(request,endpointRequest.getHttpServletRequest());

        Assert.assertEquals("http://www.foobar.com:80/foo/bar",endpointRequest.getBaseUrl());
        Assert.assertEquals("application/json",endpointRequest.getContentType());
        Assert.assertEquals("foo=bar",endpointRequest.getQueryString());
        Assert.assertEquals("GET",endpointRequest.getRequestMethod());
        Assert.assertEquals("/baz",endpointRequest.getRequestUri());


    }
}

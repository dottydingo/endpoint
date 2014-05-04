package com.dottydingo.service.endpoint.context;

import com.dottydingo.service.endpoint.configuration.EndpointConfiguration;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 */
public class ContextBuilderTest
{
    private AbstractContextBuilder contextBuilder = new TestContextBuilder();



    @Test
    public void testGetBaseUrl()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setProtocol("http");
        request.setServerName("www.foobar.com");
        request.setServletPath("/bar");
        request.setContextPath("/foo");
        request.setRequestURI("/foo/bar/baz");

        Assert.assertEquals("http://www.foobar.com:80/foo",contextBuilder.getBaseUrl(request));
    }

    @Test
    public void testCreateRequest() throws Exception
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

        MockHttpServletResponse response = new MockHttpServletResponse();

        EndpointRequest endpointRequest = contextBuilder.createRequestInstance();
        contextBuilder.setupRequest(request, response, endpointRequest);
        Assert.assertNotNull(endpointRequest);

        Assert.assertSame(request,endpointRequest.getHttpServletRequest());

        Assert.assertEquals("http://www.foobar.com:80/foo",endpointRequest.getBaseUrl());
        Assert.assertEquals("application/json",endpointRequest.getContentType());
        Assert.assertEquals("foo=bar",endpointRequest.getQueryString());
        Assert.assertEquals("GET",endpointRequest.getRequestMethod());
        Assert.assertEquals("/foo/bar/baz",endpointRequest.getRequestUri());
    }

    private class TestContextBuilder extends AbstractContextBuilder<EndpointContext,EndpointRequest,EndpointResponse>
    {
        private TestContextBuilder()
        {
            this.endpointConfiguration = new EndpointConfiguration();
        }

        @Override
        protected EndpointContext createContextInstance()
        {
            return new EndpointContext();
        }

        @Override
        protected EndpointResponse createResponseInstance()
        {
            return new EndpointResponse();
        }

        @Override
        protected EndpointRequest createRequestInstance()
        {
            return new EndpointRequest();
        }
    }
}

package com.dottydingo.service.endpoint.spring;

import com.dottydingo.service.endpoint.EndpointHandler;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class EndpointRequestHandler implements HttpRequestHandler
{
    private EndpointHandler endpointHandler;

    public void setEndpointHandler(EndpointHandler endpointHandler)
    {
        this.endpointHandler = endpointHandler;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        endpointHandler.handleRequest(request, response);
    }
}

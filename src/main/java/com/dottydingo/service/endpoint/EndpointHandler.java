package com.dottydingo.service.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public interface EndpointHandler
{
    void handleRequest(HttpServletRequest request,HttpServletResponse response);
}

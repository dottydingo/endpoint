package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.status.ContextStatus;
import com.dottydingo.service.endpoint.status.ContextStatusRegistry;
import com.dottydingo.service.pipeline.ErrorHandler;
import com.dottydingo.service.pipeline.PhaseSelector;
import com.dottydingo.service.endpoint.context.ContextBuilder;
import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.context.EndpointRequest;
import com.dottydingo.service.endpoint.context.EndpointResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 */
public interface EndpointHandler
{
    void handleRequest(HttpServletRequest request,HttpServletResponse response);
}

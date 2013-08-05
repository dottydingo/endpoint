package com.dottydingo.service.endpoint.context;

/**
 */
public interface UserContextBuilder<U extends UserContext, C extends EndpointContext>
{
    U buildUserContext(C context);
}

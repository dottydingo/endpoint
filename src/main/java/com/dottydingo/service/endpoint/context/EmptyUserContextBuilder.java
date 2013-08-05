package com.dottydingo.service.endpoint.context;

/**
 */
public class EmptyUserContextBuilder implements UserContextBuilder<UserContext,EndpointContext>
{
    @Override
    public UserContext buildUserContext(EndpointContext context)
    {
        UserContext userContext = new UserContext();
        userContext.setUserId("");
        userContext.setUserName("");

        return userContext;
    }
}

package com.dottydingo.service.endpoint.status;

import com.dottydingo.service.endpoint.context.EndpointContext;

/**
 */
public interface ContextStatusBuilder<STAT extends ContextStatus,CON extends EndpointContext>
{
    STAT buildContextStatus(CON endpointContext);
}

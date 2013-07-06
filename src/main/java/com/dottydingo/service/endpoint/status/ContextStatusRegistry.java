package com.dottydingo.service.endpoint.status;

import java.util.List;

/**
 */
public interface ContextStatusRegistry<STAT extends ContextStatus>
{
    void registerContext(Long requestId, STAT status);
    void unRegisterContext(Long requestId);
    List<STAT> getRegisteredContexts();
}

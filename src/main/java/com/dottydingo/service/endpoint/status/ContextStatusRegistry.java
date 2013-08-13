package com.dottydingo.service.endpoint.status;

import java.util.List;

/**
 */
public interface ContextStatusRegistry<STAT extends ContextStatus>
{
    void registerContext(Long requestId, STAT status);
    void unRegisterContext(Long requestId);
    List<STAT> getRegisteredContexts();

    void associateContextStatus(STAT contextStatus);
    void associateContextStatus(STAT contextStatus,Thread thread);
    void disassociateContextStatus();
    void disassociateContextStatus(Thread thread);
    STAT getContextStatus();
    STAT getContextStatus(Thread thread);

    STAT getContextStatus(Long requestId);
}

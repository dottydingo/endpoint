package com.dottydingo.service.endpoint.status;

import java.util.List;

/**
 */
public interface ContextStatusManager<STAT extends ContextStatus>
{
    void associateContextStatus(STAT contextStatus);
    void associateContextStatus(STAT contextStatus,Thread thread);
    void disassociateContextStatus();
    void disassociateContextStatus(Thread thread);
    STAT getContextStatus();
    STAT getContextStatus(Thread thread);
}

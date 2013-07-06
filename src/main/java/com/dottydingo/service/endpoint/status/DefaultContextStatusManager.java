package com.dottydingo.service.endpoint.status;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class DefaultContextStatusManager<STAT extends ContextStatus>
        implements ContextStatusManager<STAT>,ContextStatusRegistry<STAT>
{
    private Map<Long,STAT> contextStatusMap = new ConcurrentHashMap<Long, STAT>();
    private Map<Thread,STAT> threadStatusMap = new ConcurrentHashMap<Thread, STAT>();

    @Override
    public void associateContextStatus(STAT contextStatus)
    {
        associateContextStatus(contextStatus,Thread.currentThread());
    }

    @Override
    public void associateContextStatus(STAT contextStatus, Thread thread)
    {
        threadStatusMap.put(thread,contextStatus);
    }

    @Override
    public void disassociateContextStatus()
    {
        disassociateContextStatus(Thread.currentThread());
    }

    @Override
    public void disassociateContextStatus(Thread thread)
    {
        threadStatusMap.remove(thread);
    }

    @Override
    public STAT getContextStatus()
    {
        return getContextStatus(Thread.currentThread());
    }

    @Override
    public STAT getContextStatus(Thread thread)
    {
        return threadStatusMap.get(thread);
    }

    @Override
    public void registerContext(Long requestId, STAT status)
    {
        contextStatusMap.put(requestId,status);
    }

    @Override
    public void unRegisterContext(Long requestId)
    {
        contextStatusMap.remove(requestId);
    }

    @Override
    public List<STAT> getRegisteredContexts()
    {
        return new LinkedList<STAT>(contextStatusMap.values());
    }
}

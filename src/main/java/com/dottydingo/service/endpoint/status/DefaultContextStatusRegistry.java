package com.dottydingo.service.endpoint.status;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class DefaultContextStatusRegistry<STAT extends ContextStatus>
        implements ContextStatusRegistry<STAT>
{
    private Map<Long,STAT> contextStatusMap = new ConcurrentHashMap<Long, STAT>();
    private Map<Thread,STAT> threadStatusMap = new ConcurrentHashMap<Thread, STAT>();
    private Map<Long,Thread> requestIdToThreadMap = new ConcurrentHashMap<Long, Thread>();

    @Override
    public void associateContextStatus(STAT contextStatus)
    {
        associateContextStatus(contextStatus,Thread.currentThread());
    }

    @Override
    public void associateContextStatus(STAT contextStatus, Thread thread)
    {
        threadStatusMap.put(thread,contextStatus);
        requestIdToThreadMap.put(contextStatus.getRequestId(),thread);
    }

    @Override
    public void disassociateContextStatus()
    {
        disassociateContextStatus(Thread.currentThread());
    }

    @Override
    public void disassociateContextStatus(Thread thread)
    {
        STAT status = threadStatusMap.remove(thread);
        if(status != null)
            requestIdToThreadMap.remove(status.getRequestId());
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
        requestIdToThreadMap.remove(requestId);
    }

    @Override
    public List<STAT> getRegisteredContexts()
    {
        return new LinkedList<STAT>(contextStatusMap.values());
    }

    @Override
    public STAT getContextStatus(Long requestId)
    {
        return contextStatusMap.get(requestId);
    }

    @Override
    public STAT follow(Long requestId)
    {
        Thread t = requestIdToThreadMap.get(requestId);
        if(t != null)
            disassociateContextStatus(t);

        STAT status = contextStatusMap.get(requestId);
        if(status != null)
            associateContextStatus(status);

        return status;
    }
}

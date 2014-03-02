package com.dottydingo.service.endpoint.status;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 */
public class EndpointStatus
{
    private AtomicLong totalRequests = new AtomicLong(0);
    private AtomicInteger currentRequests = new AtomicInteger(0);

    public void startRequest()
    {
        currentRequests.incrementAndGet();
        totalRequests.incrementAndGet();
    }

    public void endRequest()
    {
        currentRequests.decrementAndGet();
    }

    public long getTotalRequests()
    {
        return totalRequests.get();
    }

    public int getCurrentRequests()
    {
        return currentRequests.get();
    }
}

package com.dottydingo.service.endpoint.status;

/**
 */
public class ContextTimer
{
    private final String name;
    private long startTimestamp;
    private long endTimestamp = -1;

    public ContextTimer(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public long getStartTimestamp()
    {
        return startTimestamp;
    }

    public long getEndTimestamp()
    {
        return endTimestamp;
    }

    public void start()
    {
        startTimestamp = System.currentTimeMillis();
    }

    public void stop()
    {
        endTimestamp = System.currentTimeMillis();
    }

    public long getElapsedTime()
    {
        long end = endTimestamp;
        if(end == -1)
            end = System.currentTimeMillis();

        return end - startTimestamp;
    }
}

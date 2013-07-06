package com.dottydingo.service.endpoint.status;

import com.dottydingo.service.endpoint.context.EndpointContext;

import java.util.*;

/**
 */
public class DefaultContextStatus<CON extends EndpointContext> implements ContextStatus
{
    private final CON endpointContext;
    private final Map<String,ContextTimer> timerMap = new LinkedHashMap<String, ContextTimer>();
    private final List<ContextLogEntry> logEntries = new LinkedList<ContextLogEntry>();

    public DefaultContextStatus(CON endpointContext)
    {
        this.endpointContext = endpointContext;
    }

    @Override
    public String getCorrelationId()
    {
        return endpointContext.getCorrelationId();
    }

    @Override
    public long getStartTimestamp()
    {
        return endpointContext.getStartTimestamp();
    }

    @Override
    public long getElapsedTime()
    {
        return endpointContext.getElapsedTime();
    }

    @Override
    public void startTimer(String name)
    {
        synchronized (timerMap)
        {
            ContextTimer timer = new ContextTimer(name);
            timer.start();
            timerMap.put(name,timer);
        }
    }

    @Override
    public void stopTimer(String name)
    {
        synchronized (timerMap)
        {
            ContextTimer timer = timerMap.get(name);
            if(timer != null)
            {
                timer.stop();
            }
        }
    }

    @Override
    public List<ContextTimer> getTimers()
    {
        synchronized (timerMap)
        {
            return new ArrayList<ContextTimer>(timerMap.values());
        }
    }

    @Override
    public void addContextLog(String entry)
    {
        synchronized (logEntries)
        {
            logEntries.add(new ContextLogEntry(entry));
        }
    }

    @Override
    public void addContextLog(ContextLogEntry entry)
    {
        synchronized (logEntries)
        {
            logEntries.add(entry);
        }
    }

    @Override
    public List<ContextLogEntry> getContextLogs()
    {
        synchronized (logEntries)
        {
            return new LinkedList<ContextLogEntry>(logEntries);
        }
    }
}

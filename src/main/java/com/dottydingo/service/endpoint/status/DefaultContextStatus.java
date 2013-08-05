package com.dottydingo.service.endpoint.status;

import com.dottydingo.service.endpoint.context.EndpointContext;
import com.dottydingo.service.endpoint.context.UserContext;

import java.util.*;

/**
 */
public class DefaultContextStatus<CON extends EndpointContext> implements ContextStatus
{
    protected final CON endpointContext;
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
    public Long getRequestId()
    {
        return endpointContext.getRequestId();
    }

    @Override
    public String getRemoteAddress()
    {
        return endpointContext.getEndpointRequest().getRemoteAddress();
    }

    @Override
    public String getRequestUrl()
    {
        return endpointContext.getEndpointRequest().getRequestUrl();
    }

    @Override
    public String getBaseUrl()
    {
        return endpointContext.getEndpointRequest().getBaseUrl();
    }

    @Override
    public String getRequestUri()
    {
        return endpointContext.getEndpointRequest().getRequestUri();
    }

    @Override
    public String getQueryString()
    {
        return endpointContext.getEndpointRequest().getQueryString();
    }

    @Override
    public String getContentType()
    {
        return endpointContext.getEndpointRequest().getContentType();
    }

    @Override
    public String getRequestMethod()
    {
        return endpointContext.getEndpointRequest().getRequestMethod();
    }

    @Override
    public String getAuthType()
    {
        return endpointContext.getEndpointRequest().getAuthType();
    }

    @Override
    public String getServerName()
    {
        return endpointContext.getEndpointRequest().getServerName();
    }

    @Override
    public UserContext getUserContext()
    {
        return endpointContext.getUserContext();
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

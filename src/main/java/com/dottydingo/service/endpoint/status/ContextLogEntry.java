package com.dottydingo.service.endpoint.status;

/**
 */
public class ContextLogEntry
{
    private final long timestamp;
    private final String entry;

    public ContextLogEntry(String entry)
    {
        this(System.currentTimeMillis(),entry);
    }

    public ContextLogEntry(long timestamp, String entry)
    {
        this.timestamp = timestamp;
        this.entry = entry;
    }

    public long getTimestamp()
    {
        return timestamp;
    }

    public String getEntry()
    {
        return entry;
    }
}

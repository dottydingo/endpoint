package com.dottydingo.service.endpoint.status;

import java.util.List;

/**
 */
public interface ContextStatus
{
    String getCorrelationId();

    long getStartTimestamp();
    long getElapsedTime();

    void startTimer(String name);
    void stopTimer(String name);
    List<ContextTimer> getTimers();

    void addContextLog(String entry);
    void addContextLog(ContextLogEntry entry);
    List<ContextLogEntry> getContextLogs();
}

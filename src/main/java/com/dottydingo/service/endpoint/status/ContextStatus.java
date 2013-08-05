package com.dottydingo.service.endpoint.status;

import com.dottydingo.service.endpoint.context.UserContext;

import java.util.List;

/**
 */
public interface ContextStatus
{
    Long getRequestId();
    String getCorrelationId();
    String getRemoteAddress();
    String getRequestUrl();
    String getBaseUrl();
    String getRequestUri();
    String getQueryString();
    String getContentType();
    String getRequestMethod();
    String getAuthType();
    String getServerName();
    UserContext getUserContext();

    long getStartTimestamp();
    long getElapsedTime();

    void startTimer(String name);
    void stopTimer(String name);
    List<ContextTimer> getTimers();

    void addContextLog(String entry);
    void addContextLog(ContextLogEntry entry);
    List<ContextLogEntry> getContextLogs();
}

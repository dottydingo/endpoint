package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 */
public class AsynchronousEndpointHandler<C extends EndpointContext> extends BaseEndpointHandler<C>
{
    private long timeout = 0;

    public void setTimeout(long timeout)
    {
        this.timeout = timeout;
    }

    @Override
    protected void beforePipeline(C context)
    {
        HttpServletRequest request = context.getEndpointRequest().getHttpServletRequest();
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(timeout);
        asyncContext.addListener(new EndpointAsyncListener<C>(context));
    }

    private class EndpointAsyncListener<C extends EndpointContext> implements AsyncListener
    {
        private Logger logger = LoggerFactory.getLogger(EndpointAsyncListener.class);
        private C context;

        private EndpointAsyncListener(C context)
        {
            this.context = context;
        }

        @Override
        public void onComplete(AsyncEvent event) throws IOException
        {

        }

        @Override
        public void onTimeout(AsyncEvent event) throws IOException
        {
            context.setTimedOut();
            logger.warn(String.format("Request CID: %s timed out.",context.getCorrelationId()));
        }

        @Override
        public void onError(AsyncEvent event) throws IOException
        {

        }

        @Override
        public void onStartAsync(AsyncEvent event) throws IOException
        {

        }
    }
}

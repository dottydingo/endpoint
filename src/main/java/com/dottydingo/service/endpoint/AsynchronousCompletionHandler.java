package com.dottydingo.service.endpoint;

import com.dottydingo.service.endpoint.context.EndpointContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;

/**
 */
public class AsynchronousCompletionHandler<C extends EndpointContext> extends DefaultCompletionHandler<C>
{
    private Logger logger = LoggerFactory.getLogger(AsynchronousCompletionHandler.class);

    @Override
    public void completeRequest(C endpointContext)
    {
        super.completeRequest(endpointContext);
        AsyncContext asyncContext = null;
        try
        {
            asyncContext = endpointContext.getEndpointRequest().getHttpServletRequest().getAsyncContext();
            if(asyncContext != null)
                asyncContext.complete();

        }
        catch (Exception e)
        {
            logger.error("Error retrieving asyncContext",e);
        }


    }
}

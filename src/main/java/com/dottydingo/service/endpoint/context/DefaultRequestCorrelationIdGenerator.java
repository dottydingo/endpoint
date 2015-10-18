package com.dottydingo.service.endpoint.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.dottydingo.service.endpoint.context.ConversionUtils.*;

/**
 */
public class DefaultRequestCorrelationIdGenerator<C extends EndpointContext> implements RequestCorrelationIdGenerator<C>
{
    private Logger logger = LoggerFactory.getLogger(DefaultRequestCorrelationIdGenerator.class);
    private final String hostHash;

    public DefaultRequestCorrelationIdGenerator()
    {
        String hostName = "unknown";
        InetAddress address = null;
        try
        {
            address = InetAddress.getLocalHost();
            hostName = address.getHostName();
            logger.info("Detected hostname: {}",hostName);
        }
        catch (UnknownHostException e)
        {
            logger.warn("Could not read hostname, using \"unknown\"");
        }

        hostHash = asUnsignedDecimalString(hostName.hashCode(),36);
        logger.info("Using hostname hash: {}",hostHash);

    }

    @Override
    public String generateRequestCorrelationId(C endpointContext)
    {
        return hostHash + "-" + asUnsignedDecimalString(endpointContext.getStartTimestamp(),36)
                + "-" + asUnsignedDecimalString(endpointContext.requestId,36)
                + ":" + endpointContext.getCorrelationId();
    }
}

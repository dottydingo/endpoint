package com.dottydingo.service.endpoint.context;

import java.util.UUID;

import static com.dottydingo.service.endpoint.context.ConversionUtils.*;

/**
 */
public class CompactUuidCorrlationIdGenerator implements CorrelationIdGenerator
{
    @Override
    public String generateCorrelationId()
    {
        UUID uuid = UUID.randomUUID();
        return asUnsignedDecimalString(uuid.getMostSignificantBits(),36) + asUnsignedDecimalString(uuid.getLeastSignificantBits(),36);
    }
}

package com.dottydingo.service.endpoint.context;

import java.math.BigInteger;

/**
 */
public class ConversionUtils
{
    private static final BigInteger TWO_64 = BigInteger.ONE.shiftLeft(64);

    public static String asUnsignedDecimalString(long l,int radix)
    {
        BigInteger b = BigInteger.valueOf(l);
        if (b.signum() < 0)
        {
            b = b.add(TWO_64);
        }
        return b.toString(radix);
    }
}

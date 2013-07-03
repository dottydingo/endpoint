package com.dottydingo.service.endpoint.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 */
public class BufferingInputStreamWrapper extends InputStream
{
    private final InputStream wrapped;
    private final ByteArrayOutputStream buffer;
    private final int maxBufferSize;
    private int readBytes = 0;
    private final boolean suppressClose;

    public BufferingInputStreamWrapper(InputStream wrapped, int maxBufferSize,boolean suppressClose)
    {
        this.wrapped = wrapped;
        this.maxBufferSize = maxBufferSize;
        this.buffer = new ByteArrayOutputStream(maxBufferSize);
        this.suppressClose = suppressClose;
    }

    @Override
    public int read() throws IOException
    {
        int value = wrapped.read();
        if(readBytes++ < maxBufferSize)
            buffer.write(value);

        return value;
    }

    public byte[] getBuffer()
    {
        if(readBytes > maxBufferSize)
            return null;

        return buffer.toByteArray();
    }

    public boolean bufferOverflow()
    {
        return readBytes > maxBufferSize;
    }

    @Override
    public void close() throws IOException
    {
        if(!suppressClose)
            wrapped.close();
    }
}

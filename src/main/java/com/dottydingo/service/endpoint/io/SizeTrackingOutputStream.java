package com.dottydingo.service.endpoint.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 */
public class SizeTrackingOutputStream extends OutputStream
{
    private final OutputStream wrapped;
    private long bytesWritten;
    private final boolean suppressClose;

    public SizeTrackingOutputStream(OutputStream wrapped, boolean suppressClose)
    {
        this.wrapped = wrapped;
        this.suppressClose = suppressClose;
    }

    @Override
    public void write(int b) throws IOException
    {
        wrapped.write(b);
        bytesWritten++;
    }

    public long getBytesWritten()
    {
        return bytesWritten;
    }

    @Override
    public void close() throws IOException
    {
        if(!suppressClose)
            wrapped.close();
    }
}

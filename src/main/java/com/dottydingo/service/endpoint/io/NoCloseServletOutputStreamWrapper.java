package com.dottydingo.service.endpoint.io;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 */
public class NoCloseServletOutputStreamWrapper extends ServletOutputStream
{
    private ServletOutputStream wrapped;

    public NoCloseServletOutputStreamWrapper(ServletOutputStream wrapped)
    {
        this.wrapped = wrapped;
    }

    @Override
    public void write(int b) throws IOException
    {
        wrapped.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException
    {
        wrapped.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException
    {
        wrapped.write(b, off, len);
    }

    @Override
    public void close() throws IOException
    {
        // no-op
    }
}

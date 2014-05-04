package com.dottydingo.service.endpoint.io;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Adapted from the jarkarta example CompressionResponseStream
 */
public class CompressionOutputStreamWrapper extends ServletOutputStream
{
    private HttpServletResponse response;
    private byte[] buffer;
    private int bufferSize = 0;
    private GZIPOutputStream gzipOutputStream = null;
    private boolean closed = false;
    private ServletOutputStream outputStream;

    public CompressionOutputStreamWrapper(HttpServletResponse response, int threshold) throws IOException
    {
        if (threshold < 1)
            throw new IllegalArgumentException("The value for threshold must be greater than 0");

        this.response = response;
        buffer = new byte[threshold];
        outputStream = response.getOutputStream();
    }

    public void write(int b) throws IOException
    {
        if (closed)
            return;

        if (bufferSize >= buffer.length)
            flushToGZip();

        buffer[bufferSize++] = (byte) b;
    }


    public void write(byte b[]) throws IOException
    {
        write(b, 0, b.length);
    }

    public void write(byte b[], int off, int len) throws IOException
    {
        if (closed)
            return;

        if (len == 0)
            return;

        if (len <= (buffer.length - bufferSize))
        {
            System.arraycopy(b, off, buffer, bufferSize, len);
            bufferSize += len;
            return;
        }

        flushToGZip();

        // ... and try again. Note, that bufferCount = 0 here !
        if (len <= (buffer.length - bufferSize))
        {
            System.arraycopy(b, off, buffer, bufferSize, len);
            bufferSize += len;
            return;
        }

        // write direct to gzip
        writeToGZip(b, off, len);
    }


    public void close() throws IOException
    {

        if (closed)
            return;


        if (gzipOutputStream != null)
        {
            flushToGZip();
            gzipOutputStream.close();
            gzipOutputStream = null;
        }
        else
        {
            if (bufferSize > 0)
            {
                outputStream.write(buffer, 0, bufferSize);
                bufferSize = 0;
            }
        }

        outputStream.close();
        closed = true;

    }

    public void flush() throws IOException
    {
        if (closed)
            return;

        flushToGZip();

        if (gzipOutputStream != null)
            gzipOutputStream.flush();

    }

    protected void flushToGZip() throws IOException
    {
        if (bufferSize > 0)
        {
            writeToGZip(buffer, 0, bufferSize);
            bufferSize = 0;
        }
    }


    protected void writeToGZip(byte b[], int off, int len) throws IOException
    {
        if (gzipOutputStream == null)
        {
            response.addHeader("Content-Encoding", "gzip");
            gzipOutputStream = new GZIPOutputStream(outputStream);
        }
        gzipOutputStream.write(b, off, len);
    }

}

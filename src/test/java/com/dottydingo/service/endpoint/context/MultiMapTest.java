package com.dottydingo.service.endpoint.context;

import com.dottydingo.service.endpoint.Util;
import com.dottydingo.service.endpoint.context.MultiMap;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class MultiMapTest
{
    private MultiMap map;

    @Before
    public void setUp() throws Exception
    {
        map = new MultiMap();
    }

    @Test
    public void testGet() throws Exception
    {
        Assert.assertNull(map.get("foo"));
        map.add("foo","bar");

        Assert.assertEquals(Util.asList("bar"),map.get("Foo"));

        map.add("Foo","baz");
        Assert.assertEquals(Util.asList("bar","baz"),map.get("Foo"));
    }

    @Test
    public void testGetFirst() throws Exception
    {
        Assert.assertNull(map.get("foo"));

        map.add("foo","bar");
        map.add("foo","baz");

        Assert.assertEquals("bar",map.getFirst("foo"));
    }

    @Test
    public void testAdd() throws Exception
    {
        map.add("foo","bar");
        map.add("Foo","baz");

        Assert.assertEquals(Util.asList("bar","baz"),map.get("Foo"));
    }

    @Test
    public void testGetKeys() throws Exception
    {
        map.add("foo","bar");
        map.add("baz","buzz");

        Assert.assertEquals(Util.asSet("foo","baz"),map.getKeys());
    }
}

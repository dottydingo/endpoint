package com.dottydingo.service.endpoint;

import java.util.*;

/**
 */
public class Util
{
    public static <T>List<T> asList(T... items)
    {
        return Arrays.asList(items);
    }

    public static <T> Set<T> asSet(T... items)
    {
        Set<T> set = new LinkedHashSet<T>();
        Collections.addAll(set, items);

        return set;
    }
}

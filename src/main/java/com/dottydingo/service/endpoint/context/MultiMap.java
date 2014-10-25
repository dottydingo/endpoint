package com.dottydingo.service.endpoint.context;

import java.util.*;

/**
 */
public class MultiMap
{
    private Map<String,List<String>> map = new TreeMap<String,List<String>>(new CaseInsensitiveComparator());

    public MultiMap()
    {
    }

    private MultiMap(Map<String, List<String>> map)
    {
        this.map = map;
    }

    /**
     * Return the values associated with the key.
     * @param key The key
     * @return The values, or null if there are none.
     */
    public List<String> get(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Null keys not allowed.");

        return map.get(key);
    }

    /**
     * Return the first value associated with the key.
     * @param key The key.
     * @return The first value, or null if there are no values.
     */
    public String getFirst(String key)
    {
        if(key == null)
            throw new IllegalArgumentException("Null keys not allowed.");

        List<String> vals = map.get(key);
        if(vals == null || vals.size() == 0)
            return null;

        return vals.get(0);
    }

    /**
     * Add a value to the key. If other values are already associated with the key then this value will be added
     * to the existing values.
     * @param key The key
     * @param value The value
     */
    public void add(String key, String value)
    {
        if(key == null)
            throw new IllegalArgumentException("Null keys not allowed.");

        if(value == null)
            throw new IllegalArgumentException("Null values not allowed.");

        List<String> vals = map.get(key);
        if(vals == null)
        {
            vals = new ArrayList<String>();
            map.put(key,vals);
        }

        vals.add(value);
    }

    /**
     * Return a copy of this MultiMap containing only the supplied keys
     * @param keys The keys to include in the copy
     * @return The filtered copy
     */
    public MultiMap filter(Collection<String> keys)
    {
        // no reason for a treemap here since we are copying by known keys
        Map<String,List<String>> copy = new HashMap<String, List<String>>();
        for (String key : keys)
        {
            List<String> value = map.get(key);
            if(value != null)
                copy.put(key,Collections.unmodifiableList(value));
        }

        return new MultiMap(copy);
    }

    public Set<String> getKeys()
    {
        return map.keySet();
    }

    private class CaseInsensitiveComparator implements Comparator<String>
    {
        @Override
        public int compare(String o1, String o2)
        {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }
}

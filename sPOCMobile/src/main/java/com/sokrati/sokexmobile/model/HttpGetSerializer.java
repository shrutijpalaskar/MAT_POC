package com.sokrati.sokexmobile.model;

import java.util.Map;

public class HttpGetSerializer implements ModelSerializer
{
    private static final String EQUALS = "=";
    private static final String AMPERSAND = "&";

    @Override
    public String serialize(Map<String, Object> map)
    {
        String ret = "";
        StringBuilder builder = new StringBuilder();
        for (String key : map.keySet())
        {
            Object value = map.get(key);
            builder.append(key);
            builder.append(EQUALS);
            builder.append(value.toString());
            builder.append(AMPERSAND);
        }
        if (builder.length() > 0)
        {
            ret = builder.substring(0, builder.length() - 1);
        }
        return ret;
    }

}

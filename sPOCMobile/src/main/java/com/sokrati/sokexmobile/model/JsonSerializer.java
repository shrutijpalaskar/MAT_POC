package com.sokrati.sokexmobile.model;

import java.util.Map;

import org.json.JSONObject;

public class JsonSerializer implements ModelSerializer
{

    @Override
    public String serialize(Map<String, Object> map)
    {
        JSONObject jsonObject = new JSONObject(map);
        String string = jsonObject.toString();
        return string;
    }

}

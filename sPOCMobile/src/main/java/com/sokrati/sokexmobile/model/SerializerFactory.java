package com.sokrati.sokexmobile.model;

public class SerializerFactory
{
    public static ModelSerializer getSerializer()
    {
//        ModelSerializer ret = new JsonSerializer();
        ModelSerializer ret = new HttpGetSerializer();
        return ret;
    }
}

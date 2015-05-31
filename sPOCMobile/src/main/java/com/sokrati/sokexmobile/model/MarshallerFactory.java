package com.sokrati.sokexmobile.model;

public class MarshallerFactory
{
    
    public static ModelMarshaller<Lead> getMarshaller()
    {
        ModelMarshaller<Lead> ret = new LeadMarshaller();
        return ret;
    }
}

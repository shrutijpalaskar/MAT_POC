package com.sokrati.sokexmobile.model;

import java.util.Map;

public abstract class Model
{

    public abstract Map<String, Object> marshall();     //this is the abstract method defined in Lead class

    private String serializedParams;

    public String getSerializedParams()
    {
        return serializedParams;
    }

    public void setSerializedParams(String serializedParams)
    {
        this.serializedParams = serializedParams;
    }

}
//nobody can create object of this class

// whoever extends model, will implement these methods

//lead extends model
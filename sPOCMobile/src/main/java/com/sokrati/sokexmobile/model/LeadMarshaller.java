package com.sokrati.sokexmobile.model;

import java.util.Map;

public class LeadMarshaller implements ModelMarshaller<Lead>        // implements the interface ModelMarshaller
{

    @Override
    public Map<String, Object> marshall(Lead obj)
    {
        Map<String, Object> marshall = obj.marshall();
        return marshall;
    }

}

package com.sokrati.sokexmobile.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.os.Bundle;

public class Lead extends Model
{

    private String leadName;
    private Bundle leadParamsBundle;
    private Long timestamp;

    public Lead(String leadName, Bundle leadParamsBundle, Long timestamp)
    {
        super();
        this.leadName = leadName;
        this.leadParamsBundle = leadParamsBundle;
        this.timestamp = timestamp;
    }

    public String getLeadName()
    {
        return leadName;
    }

    public void setLeadName(String leadName)
    {
        this.leadName = leadName;
    }

    public Bundle getLeadParamsBundle()
    {
        return leadParamsBundle;
    }

    public void setLeadParamsBundle(Bundle leadParamsBundle)
    {
        this.leadParamsBundle = leadParamsBundle;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }


    //implementing marshall()
    @Override
    public Map<String, Object> marshall()
    {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("sok_leadname", leadName);//makes sure at least null value exists
        Date date = new Date(timestamp);
        ret.put("sok_timestamp", date.toString());
        Set<String> keySet = leadParamsBundle.keySet();
        for (String key : keySet)
        {
            Object object = leadParamsBundle.get(key);
            ret.put(key, object);
        }
        return ret;
    }

}

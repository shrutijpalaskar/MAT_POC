package com.sokrati.sokexmobile.httpClient;

import java.util.ArrayList;

import com.sokrati.sokexmobile.model.Lead;

public class LeadUploader
{
    private String url;
    private static LeadUploader lead;
    
    private LeadUploader(String url)
    {
        this.url = url;
    }
    
    static public void initialize(String url)
    {
        if (lead == null)
        {
            synchronized(LeadUploader.class)
            {
                if (lead == null)
                {
                    lead = new LeadUploader(url);
                }
            }
        }
    }
    
    static public LeadUploader getInstance()
    {
        if (null == lead)
        {
            throw new RuntimeException("" +
                "httpClient.Lead is not initialized." +
                "Please initialize it with valid lead server url"
            );
        }
        return lead;
    }
    
    public void postLeads(Long clientId, Long appId, 
                          String androidId, ArrayList<Lead> leads)
    {
        
    }
}

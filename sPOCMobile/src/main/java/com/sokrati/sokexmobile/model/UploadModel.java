package com.sokrati.sokexmobile.model;

public class UploadModel
{

    private Integer id;
    private String payload;
    private Long timeStamp;
    
    public UploadModel(Integer id, String payload, Long timeStamp)
    {
        this.id = id;
        this.payload = payload;
        this.timeStamp = timeStamp;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getPayload()
    {
        return payload;
    }

    private void setPayload(String payload)
    {
        this.payload = payload;
    }

    public Long getTimeStamp()
    {
        return timeStamp;
    }

    private void setTimeStamp(Long timeStamp)
    {
        this.timeStamp = timeStamp;
    }

}

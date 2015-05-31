package com.sokrati.sokexmobile.model;


public class LeadData extends UploadModel
{

    public LeadData(Integer id, String payLoad, Long timeStamp)
    {
        super(id, payLoad, timeStamp);      //constructor of UploadModel
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("LeadData [payLoad=");
        builder.append(getPayload());
        builder.append(", timeStamp=");
        builder.append(getTimeStamp());
        builder.append("]");
        return builder.toString();
    }

}

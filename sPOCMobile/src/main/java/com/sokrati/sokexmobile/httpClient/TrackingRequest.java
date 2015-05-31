package com.sokrati.sokexmobile.httpClient;

public class TrackingRequest
{

    private static final String SLASH = "/";
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    private static final String CLIENTID = "client_id";
    private static final String APPID = "tag";
    private static final String SESSIONID = "session_id";
    private static final String DEVICEID = "ubid";
    private static final String DEVICENAME = "deviceName";
    private static final String COOKIELESS = "cookie_less";

    private String clientId;
    private String appid;
    //    cookie_less
    private String deviceId;//ubid=androidid
    private String deviceName;
    private String sessionid;
    //    params: url encoded
    private String paramsPayload;

    public TrackingRequest(String clientId,
                           String appid,
                           String deviceId,
                           String sessionid,
                           String paramsPayload)
    {
        super();
        this.clientId = clientId;
        this.appid = appid;
        this.deviceId = deviceId;
        this.sessionid = sessionid;
        this.paramsPayload = paramsPayload;
    }

    public TrackingRequest(String clientId,
                           String appid,
                           String deviceId,
                           String sessionid,
                           String deviceName,
                           String paramsPayload)
    {
        super();
        this.clientId = clientId;
        this.appid = appid;
        this.deviceId = deviceId;
        this.sessionid = sessionid;
        this.paramsPayload = paramsPayload;
        this.deviceName = deviceName;
    }

    public String prepareGetRequest(String host, String module)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(host);
        builder.append(SLASH);
        builder.append(module);
        builder.append(QUESTION_MARK);
        builder.append(CLIENTID);
        builder.append(EQUALS);
        builder.append(clientId);
        builder.append(AMPERSAND);
        builder.append(APPID);
        builder.append(EQUALS);
        builder.append(appid);
        builder.append(AMPERSAND);
        builder.append(DEVICEID);
        builder.append(EQUALS);
        builder.append(deviceId);
        builder.append(AMPERSAND);
        builder.append(DEVICENAME);
        builder.append(EQUALS);
        builder.append(deviceName);
        builder.append(AMPERSAND);
        builder.append(SESSIONID);
        builder.append(EQUALS);
        builder.append(sessionid);
        builder.append(AMPERSAND);
        builder.append(COOKIELESS);

        if (null != paramsPayload && !paramsPayload.isEmpty())
        {
            builder.append(AMPERSAND);
            builder.append(paramsPayload);
        }
        String ret = builder.toString();
        return ret;
    }

    public String getClientId(String clientId)
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getAppid()
    {
        return appid;
    }

    public void setAppid(String appid)
    {
        this.appid = appid;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getSessionid()
    {
        return sessionid;
    }

    public void setSessionid(String sessionid)
    {
        this.sessionid = sessionid;
    }

    public String getParamsPayload()
    {
        return paramsPayload;
    }

    public void setParamsPayload(String paramsPayload)
    {
        this.paramsPayload = paramsPayload;
    }

}

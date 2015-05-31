package com.sokrati.sokexmobile.services;

import java.io.IOException;
import java.util.List;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.sokrati.sokexmobile.httpClient.HttpTrackingClient;
import com.sokrati.sokexmobile.httpClient.SessionManager;
import com.sokrati.sokexmobile.httpClient.TrackingRequest;
import com.sokrati.sokexmobile.identification.DeviceIdentifier;
import com.sokrati.sokexmobile.model.Lead;
import com.sokrati.sokexmobile.model.LeadData;
import com.sokrati.sokexmobile.storage.LeadStorage;
import com.sokrati.sokexmobile.storage.LeadStorageHelper;
import com.sokrati.sokexmobile.storage.Storage;

public class UploadParamsService extends IntentService
{

    private Storage<Lead, LeadData> storage;
//    private String url = "http://172.132.45.163";//TODO: URL from preferences
    private String url = "http://192.168.43.65";//TODO: URL from preferences
    private String clientId = "2";//TODO: from preferences
    private String appId = "2_1";//TODO: from preferences
    
    public UploadParamsService()
    {
        super("UploadParamsService");
    }
    
    public UploadParamsService(String name)
    {
        super(name);
    }

    @Override
    public void onCreate()
    {
        LeadStorageHelper storageHelper =
            new LeadStorageHelper(getApplicationContext());
        storage = new LeadStorage(storageHelper);

        super.onCreate();
        Context applicationContext = getApplicationContext();
        DeviceIdentifier.initAndroidId(applicationContext);
        DeviceIdentifier.initDeviceName();
    }

    @Override
    protected void onHandleIntent(Intent arg0)
    {
        List<LeadData> consumableParams = storage.getConsumableParams();        //5 , the threshold
        try
        {
            for (LeadData leadData : consumableParams)
            {
                String payload = leadData.getPayload();
                String deviceId = DeviceIdentifier.getDeviceIdentifier();
                String sessionId = SessionManager.getCurrentSession();
                String deviceName = DeviceIdentifier.getDeviceName();
                TrackingRequest trackingRequest = new TrackingRequest(clientId, appId, deviceId, sessionId, deviceName, payload);
                String prepareGetRequest = trackingRequest.prepareGetRequest(url, "lead");
                prepareGetRequest = prepareGetRequest.replaceAll(" ", "+");
                HttpTrackingClient.getData(prepareGetRequest);
                storage.delete(leadData);                       // delete sent stuff coz of threshold
            }
        }
        catch (IOException e)
        {
            Log.d("UploadParamsService", "Upload failed. Retry next time.", e);
        }
    }
    
}

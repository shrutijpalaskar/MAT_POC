package com.sokrati.sokexmobile.services;

import java.util.Calendar;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sokrati.sokexmobile.httpClient.SessionManager;
import com.sokrati.sokexmobile.model.Lead;
import com.sokrati.sokexmobile.model.LeadData;
import com.sokrati.sokexmobile.model.MarshallerFactory;
import com.sokrati.sokexmobile.model.ModelMarshaller;
import com.sokrati.sokexmobile.model.ModelSerializer;
import com.sokrati.sokexmobile.model.SerializerFactory;
import com.sokrati.sokexmobile.storage.LeadStorage;
import com.sokrati.sokexmobile.storage.LeadStorageHelper;
import com.sokrati.sokexmobile.storage.Storage;
import com.sokrati.sokexmobile.ga_tracker;
import com.sokrati.sokexmobile.af_tracker;

public class PersistParamsService extends IntentService
{

    PersistenceProcessor<Lead, LeadData> leadPersistenceProcessor;
    private Storage<Lead, LeadData> storage;

    ga_tracker ga;
    af_tracker af;

    public PersistParamsService()
    {
        super("PersistParamsService");
    }

    public PersistParamsService(String name)
    {
        super(name);
    }

    private PersistenceProcessor<Lead, LeadData> initLeadPersistenceProcessor()
    {
        ga=new ga_tracker();
        af=new af_tracker();

        LeadStorageHelper storageHelper =
            new LeadStorageHelper(getApplicationContext());

        ModelSerializer serializer = SerializerFactory.getSerializer();
        ModelMarshaller<Lead> marshaller = MarshallerFactory.getMarshaller();

        storage = new LeadStorage(storageHelper);
        leadPersistenceProcessor =
            new PersistenceProcessor<Lead, LeadData>(
                marshaller, serializer, storage);
        return leadPersistenceProcessor;
    }

    @Override
    public void onCreate()
    {
        this.leadPersistenceProcessor = initLeadPersistenceProcessor();
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Bundle extras = intent.getExtras();
        String leadName = extras.getString("sok_leadname");
        long timeInMillis = Calendar.getInstance().getTimeInMillis();
        Long timestamp = extras.getLong("sok_timestamp", timeInMillis);
        Lead lead = new Lead(leadName, extras, timestamp);

        //calling appsflyer and GA functions from here--passing lead

        ga.start(lead);

       // Context cont=getApplicationContext();
       // af.start(lead,cont);

        leadPersistenceProcessor.persist(lead);
        SessionManager.ping();

        long currentStorageSize = storage.getCurrentStorageSize();
        if (currentStorageSize > 5)//TODO: timer check, threshold from preferences
        {
            Context applicationContext = getApplicationContext();
            Intent serviceIntent =
                new Intent(applicationContext, UploadParamsService.class);
            applicationContext.startService(serviceIntent);
        }
    }

    @Override
    public void onDestroy()
    {
//        SessionManager.invalidate(); //gets called each time.
        super.onDestroy();
    }
}

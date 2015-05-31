package com.sokrati.sokexmobile.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;

import com.sokrati.sokexmobile.services.PersistParamsService;

public class SokDroidLeadReceiver extends BroadcastReceiver
{
    public SokDroidLeadReceiver()
    {
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent serviceIntent = new Intent(context, PersistParamsService.class);     //need of service, same service could be used at all times for multiples apps on same device
        serviceIntent.putExtras(intent);
        context.startService(serviceIntent);        //new intent created to start service of PersistParamsService
    }

    public static String getAndroidId(Context appContext)
    {
        String mAndroidId = Secure.getString(appContext.getContentResolver(),
            Secure.ANDROID_ID);
        return mAndroidId;
    }
}

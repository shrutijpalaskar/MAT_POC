package com.sokrati.sokexmobile.receivers;

/**
 * Created by me on 1/4/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings.Secure;
import android.util.Log;

import com.sokrati.sokexmobile.MainActivity;
import com.sokrati.sokexmobile.services.PersistParamsService;

public class GAReceiver extends BroadcastReceiver {

    MainActivity m=new MainActivity();

    public GAReceiver() {}

    public void onReceive(Context context, Intent intent)
    {

        Intent serviceIntent = new Intent(context, PersistParamsService.class);
        serviceIntent.putExtras(intent);
        context.startService(serviceIntent);


    }

    public static String getAndroidId(Context appContext)
    {
        String mAndroidId = Secure.getString(appContext.getContentResolver(),
                Secure.ANDROID_ID);
        return mAndroidId;
    }
}

package com.sokrati.sokexmobile.identification;

import android.content.Context;
import android.os.Build;

public class DeviceIdentifier
{
    private static String identifier = "NOT_INITIALIZED";
    private static String name = "NOT_INITIALIZED";

    public static void initAndroidId(Context appContext)
    {
        String mAndroidId =
            android.provider.Settings.Secure.getString(
                appContext.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        if (mAndroidId == null)
        {
            mAndroidId =
                android.provider.Settings.System.getString(
                    appContext.getContentResolver(),
                    android.provider.Settings.System.ANDROID_ID);
        }
        identifier = mAndroidId;
    }

    public static void initDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer))
        {
            name = model;
        }
        else
        {
            name = manufacturer + " " + model;
        }
    }

    public static String getDeviceIdentifier()
    {
        return identifier;
    }

    public static String getDeviceName()
    {
        return name;
    }

}

package com.sokrati.sokexmobile.httpClient;

import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SessionManager
{

    private static String currentSession;
    private static Long lastPing;

    public static Long thresholdMillis = 30 * 60 * 1000l;//30 minutes

//    public static Long thresholdMillis = 1 * 60 * 1000l;//1 minute
    
    private static Lock mutex = new ReentrantLock();

    public static void ping()
    {
        Long pingTimestamp = Calendar.getInstance().getTimeInMillis();
        if (null == lastPing)
        {
            lastPing = pingTimestamp;
            invalidate();
        }
        else if ((pingTimestamp - lastPing) < thresholdMillis)
            lastPing = pingTimestamp;
        else
            invalidate();
    }

    public static String getCurrentSession()
    {
        String ret = null;
        mutex.lock();
        if(null == currentSession)
        {
            invalidate();
        }
        ret = currentSession;
        mutex.unlock();
        return ret;
    }

    public static void invalidate()
    {
        mutex.lock();
        UUID randomUUID = UUID.randomUUID();
        currentSession = randomUUID.toString();
        currentSession = currentSession.replaceAll("-", "");//32 chars max
        mutex.unlock();
    }

}

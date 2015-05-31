package com.sokrati.sokexmobile;

import android.app.Application;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.sokrati.sokexmobile.R;

import java.util.HashMap;
/**
 * Created by me on 1/4/15.
 */
public class MyApplication extends Application {

    private static final String PROPERTY_ID = "**-********-3";

    public static int GENERAL_TRACKER = 0;

    public enum TrackerName {
        APP_TRACKER,
        // Excluded Global tracker; for enterprise usage
        // Excluded Ecommerce tracker; only for ecommerce transactions
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public MyApplication() {
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
            Tracker t = analytics.newTracker(PROPERTY_ID);

            t.enableAdvertisingIdCollection(true);  //enabling tracking id collection on every tracker that is created
            mTrackers.put(trackerId, t);
        }
        return mTrackers.get(trackerId);
    }
}

/*
: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(
        R.xml.global_tracker)
        : analytics.newTracker(R.xml.ecommerce_tracker);

        */

// app tracker can now directly be created using analytics.newTRacker(property_id)

/*Note that the tracker can be created from a PROPERTY_ID using analytics.newTracker(PROPERTY_ID)
or it can be created from a xml resource file as analytics.newTracker(R.xml.global_tracker).*/
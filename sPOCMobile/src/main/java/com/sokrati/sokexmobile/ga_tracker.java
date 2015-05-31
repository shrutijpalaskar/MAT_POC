package com.sokrati.sokexmobile;

import com.google.android.gms.analytics.HitBuilders;
import com.sokrati.sokexmobile.model.Lead;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.os.Bundle;

/**
 * Created by me on 1/7/15.
 */
public class ga_tracker implements SensorEventListener
{
    MainActivity m=new MainActivity();
    public void start(Lead lead)
    {

        String leadName=lead.getLeadName();
        Bundle bun= lead.getLeadParamsBundle();
        Long timestamp=lead.getTimestamp();             //defined in Lead.java


        if(leadName.equalsIgnoreCase("dotEaten"))
        {
            event_doteaten(leadName,timestamp,bun);
        }
        if(leadName.equalsIgnoreCase("PACMAN_DYING"))
        {
            event_pacman_dying(leadName,timestamp,bun);
        }

        if(!(leadName.equalsIgnoreCase("dotEaten") || leadName.equalsIgnoreCase("PACMAN_DYING")))
        {
            set_event_name(leadName,timestamp);
        }

        Log.d("lead name sent : ",leadName);
    }
    public void set_event_name(String leadName, Long timestamp)
    {
      //  m.ga_t.enableExceptionReporting(true);
        m.ga_t.send(new HitBuilders.EventBuilder().
                setCategory("Game event").
                setAction("Game event").
                setLabel(leadName).
                setValue(0).
                build());

        Log.d("Event name sent",leadName);
        Log.d("Timestamp : ",timestamp.toString());
        System.out.println("timestamp :"+timestamp);        //not human readable
    }
    public void event_doteaten(String leadName, Long timestamp, Bundle bun)
    {
        Object is_energizer=bun.get("isEnergizer");
        Object dotsrem=bun.get("DotsRemaining");
        Object dotseat=bun.get("DotsEaten");

        if(is_energizer.toString().equalsIgnoreCase("true")) {
            m.ga_t.send(new HitBuilders.EventBuilder().
                    setCategory("Game event").
                    setAction("Game event").
                    setLabel(leadName).
                    setValue(0).
                    setCustomDimension(4, "Is Energizer ? " + is_energizer.toString()).
                    setCustomDimension(2, "Dots Remaining" + dotsrem.toString()).
                    setCustomDimension(3, "Dots Eaten" + dotseat.toString()).
                    build());
            Log.d("is energizer", is_energizer.toString());
        }
    }
    public void event_pacman_dying(String leadName, Long timestamp, Bundle bun)
    {
        Object level= bun.get("Level");
        Object dotsrem=bun.get("DotsRemaining");
        Object dotseat=bun.get("DotsEaten");

        m.ga_t.send(new HitBuilders.EventBuilder().
                setCategory("Game event").
                setAction("Game event").
                setLabel(leadName).
                setValue(0).
                build());
        m.ga_t.setScreenName("Pacman Dying");
        m.ga_t.send(new HitBuilders.ScreenViewBuilder().
                setCustomDimension(1,"Level "+level.toString()).
                setCustomDimension(2, "Dots Remaining" + dotsrem.toString()).
                setCustomDimension(3,"Dots eaten"+dotseat.toString()).
                build());
//timestamp

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

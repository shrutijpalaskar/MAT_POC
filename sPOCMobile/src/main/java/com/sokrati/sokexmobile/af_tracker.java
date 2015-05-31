package com.sokrati.sokexmobile;
import com.appsflyer.AppsFlyerLib;
import com.sokrati.sokexmobile.model.Lead;
import android.util.Log;
import android.os.Bundle;
import android.content.Context;
/**
 * Created by me on 1/9/15.
 */
public class af_tracker {

    public void start(Lead lead,Context cont) {

        String leadName = lead.getLeadName();
        Bundle bun = lead.getLeadParamsBundle();
        Long timestamp=lead.getTimestamp();

        if(leadName.equalsIgnoreCase("dotEaten"))
        {
            event_doteaten(leadName,timestamp, bun, cont);
        }
        if(leadName.equalsIgnoreCase("PACMAN_DYING"))
        {
            event_pacman_dying(leadName,timestamp, bun, cont);
        }

        if(!(leadName.equalsIgnoreCase("dotEaten") || leadName.equalsIgnoreCase("PACMAN_DYING")))
        {
            set_event_name(leadName,timestamp,cont);
        }
    }

    public void set_event_name(String leadName, Long timestamp,Context cont)
    {
        AppsFlyerLib.sendTrackingWithEvent(cont, "Lead Name", leadName);
    }
    public void event_doteaten(String leadName, Long timestamp, Bundle bun, Context cont)
    {
        Object is_energizer=bun.get("isEnergizer");
        Object dotsrem=bun.get("DotsRemaining");
        Object dotseat=bun.get("DotsEaten");

        AppsFlyerLib.sendTrackingWithEvent(cont,"Lead Name",leadName);
        AppsFlyerLib.sendTrackingWithEvent(cont,"isEnergizer?",String.valueOf(is_energizer));
        AppsFlyerLib.sendTrackingWithEvent(cont,"Dots Remaining",String.valueOf(dotsrem));
        AppsFlyerLib.sendTrackingWithEvent(cont,"Dots Eaten",String.valueOf(dotseat));


    }
    public void event_pacman_dying(String leadName, Long timestamp, Bundle bun, Context cont)
    {
        Object dotsrem=bun.get("DotsRemaining");
        Object dotseat=bun.get("DotsEaten");
        Object level=bun.get("Level");

        AppsFlyerLib.sendTrackingWithEvent(cont,"Lead Name",leadName);
        AppsFlyerLib.sendTrackingWithEvent(cont,"Level",String.valueOf(level));
        AppsFlyerLib.sendTrackingWithEvent(cont,"Dots Remaining",String.valueOf(dotsrem));
        AppsFlyerLib.sendTrackingWithEvent(cont,"Dots Eaten",String.valueOf(dotseat));


    }


}

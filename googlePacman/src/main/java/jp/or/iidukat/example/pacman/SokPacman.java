package jp.or.iidukat.example.pacman;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SokPacman
{
    private static String ACTION = "jp.or.iidukat.example.pacman.SOKLEAD";

    public static void publish(Context context,
                               String leadName,
                               String... params)
    {
        Intent intent = new Intent(ACTION);
        intent.putExtra("sok_leadname", leadName);
                                                        //leadname holds event label

        if (null != params && params.length > 0)
        {
            boolean isName = true;
            String name = null, value;
            for (int i = 0; i < params.length; i++)
            {
                if (isName)
                {
                    name = params[i];
                    isName = false;
                }
                else
                {
                    value = params[i];
                    intent.putExtra(name, value);
                    isName = true;
                }
            }
        }
        context.sendBroadcast(intent);
    }
}

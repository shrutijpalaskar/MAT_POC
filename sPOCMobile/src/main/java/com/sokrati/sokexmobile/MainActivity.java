package com.sokrati.sokexmobile;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.sokrati.sokexmobile.MyApplication.TrackerName;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import java.lang.Math;

public class MainActivity extends Activity implements SensorEventListener
{

    public static Tracker ga_t;
    private SensorManager sensorManager;
    private SensorManager sensorManager2;
    private Sensor accelerometer;
    private Sensor gyro;
    private float vibrateThreshold = 0;
    public Vibrator v;
    private float timestamp;

    private float lastX, lastY, lastZ, deltaXMax=0, deltaYMax=0, deltaZMax=0, deltaX=0, deltaY=0, deltaZ=0, currentX=0, currentY=0,currentZ=0;

    public void get_tracker()
    {
        ga_t=((MyApplication) getApplication()).getTracker(TrackerName.APP_TRACKER);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        get_tracker();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager2 = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an accelerometer
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        }
        else {
            // failure! we dont have an accelerometer!
            System.out.println("Accelerometer not available ");
        }
        if (sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!=null) {
            gyro = sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager2.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            System.out.println("Gyroscope not available");
        }
        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager2.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
    }
    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
        sensorManager2.unregisterListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        String accuracyMsg = "";
        switch(accuracy){
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                accuracyMsg="Sensor has high accuracy";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                accuracyMsg="Sensor has medium accuracy";
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                accuracyMsg="Sensor has low accuracy";
                break;
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                accuracyMsg="Sensor has unreliable accuracy";
                break;
            default:
                break;
        }
        Log.d("Accuracy msg : ",accuracyMsg);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {


            // clean current values
            CleanValues();
            // display the current x,y,z accelerometer values
            displayCurrentValues();
            // get the change of the x,y,z values of the accelerometer
            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);
            // if the change is below 2, it is just plain noise
            if (deltaX < 2)
                deltaX = 0;
            if (deltaY < 2)
                deltaY = 0;
            if ((deltaZ > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
                v.vibrate(50);
            }
        }

        if (sensor.getType() == Sensor.TYPE_GYROSCOPE)
        {
            // This timestep's delta rotation to be multiplied by the current rotation
            // after computing it from the gyro sample data.

            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample if required



            ga_t.send(new HitBuilders.EventBuilder().
                    setCategory("Sensor Data").
                    setAction("Sensor Data").
                    setLabel("Gyroscope").
                    setValue(0).
                    setCustomDimension(8, "Angular Speed X" + axisX).
                    setCustomDimension(9, "Angular Speed Y" + axisY).
                    setCustomDimension(10, "Angular Speed Z" + axisZ).
                    build());

        }

    }
    public void CleanValues()
    {
        currentX= (float) 0.0;
        currentY= (float) 0.0;
        currentZ= (float) 0.0;
    }
            // display the current x,y,z accelerometer values
    public void displayCurrentValues()
    {
        currentX=deltaX;
        currentY=deltaY;
        currentZ=deltaZ;
        ga_t.send(new HitBuilders.EventBuilder().
                setCategory("Sensor Data").
                setAction("Sensor Data").
                setLabel("Accelerometer").
                setValue(0).
                setCustomDimension(5, "X-Axis" + currentX).
                setCustomDimension(6, "Y-Axis" + currentY).
                setCustomDimension(7, "Z-Axis" + currentZ).
                build());
        System.out.println("Xc : "+currentX);
        System.out.println("Yc : "+currentY);
        System.out.println("Zc : "+currentZ);
    }


}

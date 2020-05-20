package edu.monash.student.happyactive.ActivityPackages;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import edu.monash.student.happyactive.ActivityPackages.stepCounter.SimpleStepDetector;
import edu.monash.student.happyactive.ActivityPackages.stepCounter.StepListener;

/**
 * Service for calculating steps from acceleration sensor
 */
public class StepCounterService extends Service implements SensorEventListener, StepListener {

    private SimpleStepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    private final IBinder mBinder = new LocalBinder();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        simpleStepDetector = new SimpleStepDetector();
        simpleStepDetector.registerListener(this);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    @Override
    public void step(long timeNs) {
        numSteps++;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    public class LocalBinder extends Binder {
        public StepCounterService getService() {
            return StepCounterService.this;
        }
    }

    public int getNumSteps(){
        return numSteps;
    }
}

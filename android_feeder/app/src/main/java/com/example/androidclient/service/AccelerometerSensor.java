package com.example.androidclient.service;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.androidclient.configs.Vector4;

public class AccelerometerSensor extends SensorBase {
    private Vector4 acceleration;

    public AccelerometerSensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        acceleration = new Vector4(0, 0, 0, 0);
    }

    @Override
    public Vector4 GetData(boolean readSensor) {
        float deltaTime = data.w;

        acceleration.z = data.z;
        acceleration.y = data.y;
        acceleration.x = data.x;
        acceleration.w = data.w;

        Log.d("Accelerometer data", acceleration.toString());
        return acceleration;
    }
}

package com.example.androidclient.service;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.androidclient.configs.Vector4;

public class GyroscopeSensor extends SensorBase {

    private Vector4 angles;

    public GyroscopeSensor(SensorManager sensorManager) {
        super(sensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        angles = new Vector4(0, 0, 0, 0);
    }

    @Override
    public Vector4 GetData(boolean readSensor) {
        float deltaTime = data.w;
        if(readSensor) {
            angles.z = angles.z + (data.z * deltaTime);
            angles.y = angles.y + (data.y * deltaTime);
            angles.x = angles.x + (data.x * deltaTime);
        }else{
            angles.z = 0;
            angles.y = 0;
            angles.x = 0;
        }
        angles.w = data.w;

        Log.d("gyroscope data", angles.toString());
        return angles;
    }
}

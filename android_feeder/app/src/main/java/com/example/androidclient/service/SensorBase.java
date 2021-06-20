package com.example.androidclient.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.androidclient.configs.Constants;
import com.example.androidclient.configs.Vector4;


public class SensorBase implements SensorEventListener {

    protected float deltaTime;
    protected float timestamp;
    protected Sensor sensor;
    protected SensorManager sensorManager;
    protected Vector4 data;
    private Vector4 calibration;

    public Vector4 GetData(boolean readSensor) {
        return GetRawData();
    }

    public Vector4 GetRawData() {
        return data;
    }

    public SensorBase(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
        data = new Vector4(0, 0, 0, 0);
        calibration = new Vector4(0, 0, 0, 0);
    }

    public void setCalibrationValue(float x, float y, float z){
        calibration.Set(x, y, z, 0);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        deltaTime = (event.timestamp - timestamp) * Constants.ns2s;
        if (timestamp != 0) {
            data.Set(
                    event.values[0] - calibration.x,
                    event.values[1] - calibration.y,
                    event.values[2] - calibration.z,
                    deltaTime
            );
        }
        timestamp = event.timestamp;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

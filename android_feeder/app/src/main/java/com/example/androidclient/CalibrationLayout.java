package com.example.androidclient;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Vector4;
import com.example.androidclient.service.AccelerometerSensor;
import com.example.androidclient.service.GyroscopeSensor;
import com.example.androidclient.service.SensorBase;

public class CalibrationLayout extends AppCompatActivity {
    private boolean isBackPressed;
    Button captureBtn;
    private SensorManager sensorManager;
    private SensorBase gyroscopeEventListener;
    private SensorBase accelerometerEventListener;
    private Vector4 gyroscopeData;
    private Vector4 accelormeterData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        captureBtn = findViewById(R.id.captureButton);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        gyroscopeEventListener = new GyroscopeSensor(sensorManager);
        accelerometerEventListener = new AccelerometerSensor(sensorManager);

        isBackPressed = false;

        gyroscopeData = gyroscopeEventListener.GetData(true);
        accelormeterData = accelerometerEventListener.GetData(false);

        captureBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("layout", " layout1");
                for(int i = 0; i < 1000; i++){
                    gyroscopeData = gyroscopeData.add(gyroscopeEventListener.GetData(true));
                    accelormeterData = accelormeterData.add(accelerometerEventListener.GetData(true));
                }
                Intent layout;
                if(getIntent().getStringExtra("layout").equals("driving")) {
                    layout = new Intent(CalibrationLayout.this, SensorLayout.class);
                }else{
                    layout = new Intent(CalibrationLayout.this, GunLayout.class);
                }
                layout.putExtra("gyroX", gyroscopeData.x/1000);
                layout.putExtra("gyroY", gyroscopeData.y/1000);
                layout.putExtra("gyroZ", gyroscopeData.z/1000);
                layout.putExtra("accX", accelormeterData.x/1000);
                layout.putExtra("accY", accelormeterData.y/1000);
                layout.putExtra("accZ", accelormeterData.z/1000);
                startActivity(layout);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackPressed = true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isBackPressed)
            Connection.getInstance().closeConnection();
    }


}

package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;
import com.example.androidclient.configs.Vector4;
import com.example.androidclient.service.AccelerometerSensor;
import com.example.androidclient.service.LayoutBase;
import com.example.androidclient.service.SensorBase;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GunLayout extends LayoutBase {

    private Button btnY, btnX, btnB, btnA, btnStart, btnBack, btnLT, btnLB, btnRT, btnRB, sensorButton;


    private SensorManager sensorManager;
    private SensorBase accelerometerEventListener;


    private int sensorDataX = 0, sensorDataY = 0;
    private boolean readSensor = false;
    private Map<String, Integer> leftJoystickValues = new HashMap<String, Integer>() {{
        put("X", 0);
        put("Y", 0);
    }};
    private Map<String, Integer> rightJoystickValues = new HashMap<String, Integer>() {{
        put("X", 0);
        put("Y", 0);
    }};


    private JSONObject data = new JSONObject();

    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_first_person_action_layout);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerEventListener = new AccelerometerSensor(sensorManager);

        Log.d("gyroX", String.valueOf(getIntent().getFloatExtra("gyroX", 0.0f)));
        Log.d("accX", String.valueOf(getIntent().getFloatExtra("accX", 0.0f)));

        accelerometerEventListener.setCalibrationValue(getIntent().getFloatExtra("accX", 0.0f), getIntent().getFloatExtra("accY", 0.0f),getIntent().getFloatExtra("accZ", 0.0f));

        JoystickView leftJoystick = findViewById(R.id.leftJoystickView);
        JoystickView rightJoystick = findViewById(R.id.rightJoystickView);


        btnA = findViewById(R.id.buttonA);
        btnB = findViewById(R.id.buttonB);
        btnX = findViewById(R.id.buttonX);
        btnY = findViewById(R.id.buttonY);
        btnStart = findViewById(R.id.buttonStart);
        btnBack = findViewById(R.id.buttonBack);
        btnRT = findViewById(R.id.buttonRT);
        btnRB = findViewById(R.id.buttonRB);
        btnLT = findViewById(R.id.buttonLT);
        btnLB = findViewById(R.id.buttonLB);
        ClickEvent(btnA, Constants.BUTTON_A_BIT, getResources().getColor(R.color.green));
        ClickEvent(btnB, Constants.BUTTON_B_BIT, getResources().getColor(R.color.red));
        ClickEvent(btnX, Constants.BUTTON_X_BIT, getResources().getColor(R.color.cyan));
        ClickEvent(btnY, Constants.BUTTON_Y_BIT, getResources().getColor(R.color.yellow));
        ClickEvent(btnStart, Constants.BUTTON_START_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnBack, Constants.BUTTON_BACK_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnRT, Constants.BUTTON_RT_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnRB, Constants.BUTTON_RB_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnLT, Constants.BUTTON_LT_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnLB, Constants.BUTTON_LB_BIT, getResources().getColor(R.color.black));



        leftJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                leftJoystickValues.put("X", (int) (strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                leftJoystickValues.put("Y", (int) (strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, 16);

        rightJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                rightJoystickValues.put("X", (int) (strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                rightJoystickValues.put("Y", (int) (strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, 16);


        new Thread(new SendThread()).start();
    }

    class SendThread implements Runnable {
        @Override
        public void run() {
            Connection connection = Connection.getInstance();

            sendTimer = new Timer();
            sendTimer.scheduleAtFixedRate(new TimerTask() {
                                              @Override
                                              public void run() {
                                                  try {
                                                      readSensorData();
                                                      data.put("buttons", number);
                                                      data.put("left_X", leftJoystickValues.get("X"));
                                                      data.put("left_Y", leftJoystickValues.get("Y"));
                                                      data.put("right_X", rightJoystickValues.get("X"));
                                                      data.put("right_Y", rightJoystickValues.get("Y"));

                                                  } catch (JSONException e) {
                                                      e.printStackTrace();
                                                  }
                                                  connection.send(data.toString());
//                                          number = 0;

                                              }
                                          },
                    0, 16);


        }
    }



    private void readSensorData(){
        Vector4 accData = accelerometerEventListener.GetData(true);

        reload(accData.x);
        changeGun(accData.y);
    }

    private void reload(float accY){
        if(Math.abs(accY) > 15){
            number |= Constants.BUTTON_X_BIT;
        }else if(Math.abs(accY) > 2){
            number &= ~Constants.BUTTON_X_BIT;
        }
    }

    private void changeGun(float accX){
        if(Math.abs(accX) > 17){
            number ^= Constants.BUTTON_Y_BIT;
        }else if(Math.abs(accX) > 2){
            number &= ~Constants.BUTTON_Y_BIT;
        }
    }

}

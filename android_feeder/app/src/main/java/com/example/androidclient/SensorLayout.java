package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;
import com.example.androidclient.service.AccelerometerSensor;
import com.example.androidclient.service.GyroscopeSensor;
import com.example.androidclient.service.LayoutBase;
import com.example.androidclient.service.SensorBase;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SensorLayout extends LayoutBase {

    private Button btnY, btnX, btnB, btnA, btnStart, btnBack, btnLT, btnLB, btnRT, btnRB, sensorButton;


    private SensorManager sensorManager;
    private SensorBase gyroscopeEventListener;
    private SensorBase accelerometerEventListener;


    private int sensorDataX = 0;
    private boolean readSensor = false;
    private Map<String, Integer> leftJoystickValues = new HashMap<String, Integer>() {{
        put("X", 0);
        put("Y", 0);
    }};

    private JSONObject data = new JSONObject();

    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sensor_layout);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroscopeEventListener = new GyroscopeSensor(sensorManager);
        accelerometerEventListener = new AccelerometerSensor(sensorManager);

        Log.d("gyroX", String.valueOf(getIntent().getFloatExtra("gyroX", 0.0f)));
        Log.d("accX", String.valueOf(getIntent().getFloatExtra("accX", 0.0f)));

        gyroscopeEventListener.setCalibrationValue(getIntent().getFloatExtra("gyroX", 0.0f),  getIntent().getFloatExtra("gyroY", 0.0f),  getIntent().getFloatExtra("gyroZ", 0.0f));
        accelerometerEventListener.setCalibrationValue(getIntent().getFloatExtra("accX", 0.0f), getIntent().getFloatExtra("accY", 0.0f),getIntent().getFloatExtra("accZ", 0.0f));

        JoystickView leftJoystick = findViewById(R.id.joystickView);

//        steerView = findViewById(R.id.steerView);

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
        sensorButton = findViewById(R.id.sensorButton);

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

        sensorButton.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                sensorButton.setBackgroundColor(getResources().getColor(R.color.clickedBtn));
                readSensor = true;
                VibrateBtn();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                readSensor = false;
                sensorButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
            }
            return true;
        });


        leftJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                leftJoystickValues.put("X", (int) (strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                leftJoystickValues.put("Y", (int) (strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, 16);


        new Thread(new SendThread()).start();
    }

    class SendThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
//            int pivotX = steerView.getDrawable().getBounds().width()/2, pivotY = steerView.getDrawable().getBounds().height()/2;
//            Matrix matrix = new Matrix();
//            steerView.setScaleType(ImageView.ScaleType.MATRIX);

            sendTimer = new Timer();
            sendTimer.scheduleAtFixedRate(new TimerTask() {
                                              @Override
                                              public void run() {
                                                  try {
                                                      readSensorData();
                                                      data.put("buttons", number);
                                                      data.put("left_X", sensorDataX);
                                                      data.put("left_Y", leftJoystickValues.get("Y"));
                                                      data.put("right_X", 0);
                                                      data.put("right_Y", 0);

//                                                      runOnUiThread(new Runnable() {
//
//                                                          @SuppressLint("SetTextI18n")
//                                                          @Override
//                                                          public void run() {
//                                                              matrix.postRotate((float) (Math.toDegrees(sensorDataX / Constants.JOYSTICK_RANGE_NUM)), steerView.getDrawable().getBounds().width()/2, steerView.getDrawable().getBounds().height()/2);
//                                                              steerView.setImageMatrix(matrix);
//                                                              // Stuff that updates the UI
//                                                          }
//                                                      });

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


    private int readSensorData(){
        gyroscopeEventListener.GetData(readSensor);
        if(readSensor){
            sensorDataX = (int)(-gyroscopeEventListener.GetData(true).z * 2.2 * Constants.JOYSTICK_RANGE_NUM);
            sensorDataX = (int)(Math.signum(sensorDataX) * 9500 + sensorDataX);
            sensorDataX = Math.abs(sensorDataX) >  Constants.JOYSTICK_RANGE_NUM ? (int)(Math.signum(sensorDataX) * Constants.JOYSTICK_RANGE_NUM) : sensorDataX;
        }else{
            sensorDataX = 0;
        }

        return sensorDataX;
    }
}

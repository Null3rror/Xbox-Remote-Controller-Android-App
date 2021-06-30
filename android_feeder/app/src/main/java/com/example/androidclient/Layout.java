package com.example.androidclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;
import com.example.androidclient.service.LayoutBase;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class Layout extends LayoutBase {

    private Button btnY,btnX,btnB,btnA,btnStart,btnBack,btnLT,btnLB,btnRT,btnRB;
    private ImageButton btnUp,btnDown,btnLeft,btnRight;



    private Map<String, Integer> leftJoystickValues = new HashMap<String, Integer>(){{
        put("X", 0);
        put("Y", 0);
    }};
    private Map<String, Integer> rightJoystickValues = new HashMap<String, Integer>(){{
        put("X", 0);
        put("Y", 0);
    }};

    private JSONObject data = new JSONObject();

    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_layout);

        JoystickView leftJoystick = (JoystickView) findViewById(R.id.leftJoystickView);
        JoystickView rightJoystick = (JoystickView) findViewById(R.id.rightJoystickView);

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
        btnUp = findViewById(R.id.imageButtonUp);
        btnDown = findViewById(R.id.imageButtonDown);
        btnRight = findViewById(R.id.imageButtonRight);
        btnLeft = findViewById(R.id.imageButtonLeft);


        ClickEvent(btnA, Constants.BUTTON_A_BIT, getResources().getColor(R.color.green));
        ClickEvent(btnB, Constants.BUTTON_B_BIT, getResources().getColor(R.color.red));
        ClickEvent(btnX, Constants.BUTTON_X_BIT, getResources().getColor(R.color.cyan));
        ClickEvent(btnY, Constants.BUTTON_Y_BIT, getResources().getColor(R.color.yellow));
        ClickEvent(btnUp, Constants.BUTTON_UP_BIT, getResources().getColor(R.color.white));
        ClickEvent(btnDown, Constants.BUTTON_DOWN_BIT, getResources().getColor(R.color.white));
        ClickEvent(btnRight, Constants.BUTTON_RIGHT_BIT, getResources().getColor(R.color.white));
        ClickEvent(btnLeft, Constants.BUTTON_LEFT_BIT, getResources().getColor(R.color.white));
        ClickEvent(btnStart, Constants.BUTTON_START_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnBack, Constants.BUTTON_BACK_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnRT, Constants.BUTTON_RT_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnRB, Constants.BUTTON_RB_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnLT, Constants.BUTTON_LT_BIT, getResources().getColor(R.color.black));
        ClickEvent(btnLB, Constants.BUTTON_LB_BIT, getResources().getColor(R.color.black));



        leftJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                leftJoystickValues.put("X", (int)(strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                leftJoystickValues.put("Y", (int)(strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, Constants.fpsRate);


        rightJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                rightJoystickValues.put("X", (int)(strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                rightJoystickValues.put("Y", (int)(strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, Constants.fpsRate);


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
                    0, Constants.fpsRate);


        }
    }

}
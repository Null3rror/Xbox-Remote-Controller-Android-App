package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.configs.Constants;
import com.example.androidclient.configs.Connection;
import com.example.androidclient.service.VibrationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Layout extends AppCompatActivity {

    private Button btnY,btnX,btnB,btnA,btnStart,btnBack,btnLT,btnLB,btnRT,btnRB;
    private ImageButton btnUp,btnDown,btnLeft,btnRight;
    private Timer sendTimer;

    VibrationService vibrationService;

    private int number = 0;
    private Map<String, Integer> leftJoystickValues = new HashMap<String, Integer>(){{
        put("X", 0);
        put("Y", 0);
    }};
    private Map<String, Integer> rightJoystickValues = new HashMap<String, Integer>(){{
        put("X", 0);
        put("Y", 0);
    }};

    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;
    private JSONObject data = new JSONObject();

    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 9) {
            setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }


        setContentView(R.layout.activity_layout);

        vibrationService = new VibrationService((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));
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
        }, 16);


        rightJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                rightJoystickValues.put("X", (int)(strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                rightJoystickValues.put("Y", (int)(strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, 16);

        new Thread(new ReceiveThread()).start();
        new Thread(new SendThread()).start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d("stop", "onDestroy");
        sendTimer.cancel();
        vibrationService.cancel();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            event.startTracking();
            number |= Constants.BUTTON_RS_BIT;
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event.startTracking();
            number ^= Constants.BUTTON_LS_BIT;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {

            event.startTracking();
            number &= ~(Constants.BUTTON_RS_BIT);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    class ReceiveThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            String message = "";
            do {
                message = connection.receive();
            }while (!message.equals("bye"));
        }
    }

    class SendThread implements Runnable { // send data to server
     //   private Integer counter = 0;

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
                    0, 16);


        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void ClickEvent(Button btn, int btnValue, int color){
        btn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= btnValue;
                btn.setBackgroundColor(getResources().getColor(R.color.clickedBtn));
                VibrateBtn();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(btnValue);
                btn.setBackgroundColor(color);
            }
            return true;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void ClickEvent(ImageButton btn, int btnValue, int color){
        btn.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= btnValue;
                btn.setBackgroundColor(getResources().getColor(R.color.clickedBtn));
                VibrateBtn();
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(btnValue);
                btn.setBackgroundColor(color);
            }
            return true;
        });
    }

    private void VibrateBtn() {
        vibrationService.vibrate(Constants.VibrationRate, VibrationEffect.DEFAULT_AMPLITUDE);
    }
}
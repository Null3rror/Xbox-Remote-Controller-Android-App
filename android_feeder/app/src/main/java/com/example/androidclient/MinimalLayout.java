package com.example.androidclient;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MinimalLayout extends AppCompatActivity {

    private Button btnY, btnX, btnB, btnA, btnStart, btnBack, btnLT, btnLB, btnRT, btnRB;

    private int number = 0;
    private Map<String, Integer> leftJoystickValues = new HashMap<String, Integer>() {{
        put("X", 0);
        put("Y", 0);
    }};
    private Map<String, Integer> rightJoystickValues = new HashMap<String, Integer>() {{
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


        setContentView(R.layout.activity_minimal_layout);


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


        btnA.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_A_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_A_BIT);
            }
            return true;
        });

        btnB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_B_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_B_BIT);
            }
            return true;
        });

        btnX.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_X_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_X_BIT);
            }
            return true;
        });

        btnY.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_Y_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_Y_BIT);
            }
            return true;
        });

        btnStart.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_START_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_START_BIT);
            }
            return true;
        });

        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_BACK_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_BACK_BIT);
            }
            return true;
        });

        btnRT.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_RT_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_RT_BIT);
            }
            return true;
        });

        btnRB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_RB_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_RB_BIT);
            }
            return true;
        });

        btnLT.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_LT_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_LT_BIT);
            }
            return true;
        });

        btnLB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_LB_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_LB_BIT);
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


        rightJoystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(double angle, double strength) {
                rightJoystickValues.put("X", (int) (strength * Math.cos(angle) * Constants.JOYSTICK_RANGE_NUM));
                rightJoystickValues.put("Y", (int) (strength * Math.sin(angle) * Constants.JOYSTICK_RANGE_NUM));
            }
        }, 16);


        new Thread(new ReceiveThread()).start();
        new Thread(new SendThread()).start();
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
            } while (!message.equals("bye"));
        }
    }

    class SendThread implements Runnable { // send data to server
        //   private Integer counter = 0;

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
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
}

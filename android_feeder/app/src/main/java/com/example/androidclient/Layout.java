package com.example.androidclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.configs.Constants;
import com.example.androidclient.configs.Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Layout extends AppCompatActivity {

    Button btnY,btnX,btnB,btnA,btnStart,btnBack,btnLS,btnRS,btnLT,btnLB,btnRT,btnRB,btn13;
    ImageButton btnUp,btnDown,btnLeft,btnRight;

    int number = 0;
    JSONObject data = new JSONObject();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_layout);



        btnA = findViewById(R.id.buttonA);
        btnB = findViewById(R.id.buttonB);
        btnX = findViewById(R.id.buttonX);
        btnY = findViewById(R.id.buttonY);
        btnStart = findViewById(R.id.buttonStart);
        btnBack = findViewById(R.id.buttonBack);
        btnRS = findViewById(R.id.buttonRS);
        btnLS = findViewById(R.id.buttonLS);
        btnRT = findViewById(R.id.buttonRT);
        btnRB = findViewById(R.id.buttonRB);
        btnLT = findViewById(R.id.buttonLT);
        btnLB = findViewById(R.id.buttonLB);
        btn13 = findViewById(R.id.button13);
        btnUp = findViewById(R.id.imageButtonUp);
        btnDown = findViewById(R.id.imageButtonDown);
        btnRight = findViewById(R.id.imageButtonRight);
        btnLeft = findViewById(R.id.imageButtonLeft);



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

        btnUp.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_UP_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_UP_BIT);
            }
            return true;
        });

        btnDown.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_DOWN_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_DOWN_BIT);
            }
            return true;
        });

        btnRight.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_RIGHT_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_RIGHT_BIT);
            }
            return true;
        });

        btnLeft.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_LEFT_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_LEFT_BIT);
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

        btnRS.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_RS_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_RS_BIT);
            }
            return true;
        });

        btnLS.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_LS_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_LS_BIT);
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

        btn13.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= Constants.BUTTON_13_BIT;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(Constants.BUTTON_13_BIT);
            }
            return true;
        });


        new Thread(new ReceiveThread()).start();
        new Thread(new SendThread()).start();
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
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          try {
                                              data.put("buttons", number);
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
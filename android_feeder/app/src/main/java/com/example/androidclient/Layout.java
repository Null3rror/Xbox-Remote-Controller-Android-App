package com.example.androidclient;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.connection.Connection;

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
                number |= 1;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<0);
            }
            return true;
        });

        btnB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 2;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<1);
            }
            return true;
        });

        btnX.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 4;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<2);
            }
            return true;
        });

        btnY.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 8;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<3);
            }
            return true;
        });

        btnUp.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 16;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<4);
            }
            return true;
        });

        btnDown.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 32;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<5);
            }
            return true;
        });

        btnRight.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 64;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<6);
            }
            return true;
        });

        btnLeft.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 128;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<7);
            }
            return true;
        });

        btnStart.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 256;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<8);
            }
            return true;
        });

        btnBack.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 512;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<9);
            }
            return true;
        });

        btnRS.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 1024;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<10);
            }
            return true;
        });

        btnLS.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 2048;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<11);
            }
            return true;
        });

        btnRT.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 4096;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<12);
            }
            return true;
        });

        btnRB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 8192;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<13);
            }
            return true;
        });

        btnLT.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 16384;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<14);
            }
            return true;
        });

        btnLB.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 32768;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<15);
            }
            return true;
        });

        btn13.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                number |= 65536;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                number &= ~(1<<16);
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
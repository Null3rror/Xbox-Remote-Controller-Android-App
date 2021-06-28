package com.example.androidclient.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.androidclient.R;
import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;

import java.util.Timer;

public abstract class LayoutBase extends AppCompatActivity {

    
    protected Timer sendTimer;
    protected boolean isBackPressed;

    VibrationService vibrationService;
    protected int number = 0;
    
    public static final int SCREEN_ORIENTATION_SENSOR_LANDSCAPE = 6;


    @SuppressLint({"ClickableViewAccessibility", "WrongConstant"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setRequestedOrientation(SCREEN_ORIENTATION_SENSOR_LANDSCAPE);

        isBackPressed = false;
        vibrationService = new VibrationService((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

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


    @SuppressLint("ClickableViewAccessibility")
    protected void ClickEvent(Button btn, int btnValue, int color){
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
    protected void ClickEvent(ImageButton btn, int btnValue, int color){
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


    protected void VibrateBtn() {
        vibrationService.vibrate(Constants.VibrationRate, VibrationEffect.DEFAULT_AMPLITUDE);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackPressed = true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendTimer.cancel();
        vibrationService.cancel();
        if (!isBackPressed)
            Connection.getInstance().closeConnection();
    }
}
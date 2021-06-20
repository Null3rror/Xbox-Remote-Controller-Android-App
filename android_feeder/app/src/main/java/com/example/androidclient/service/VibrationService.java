package com.example.androidclient.service;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.example.androidclient.configs.Constants;

public class VibrationService {
    Vibrator vibrator;

    public VibrationService(){
    }

    public VibrationService(Vibrator v){
        vibrator = v;
    }

    public void vibrate(int vibrationRate, int vibrationAmplitude){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(vibrationRate, vibrationAmplitude));
        }else{
            vibrator.vibrate(vibrationRate);
        }
    }

    public void cancel(){
        vibrator.cancel();
    }
}

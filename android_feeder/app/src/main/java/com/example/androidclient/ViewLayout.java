package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidclient.configs.Connection;


@SuppressLint("SetTextI18n")
public class ViewLayout extends AppCompatActivity {

    private boolean isBackPressed = false;
    ImageView layout1, minimalLayout, sensorLayout , xboxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_view_layout);

        isBackPressed = false;
        layout1 = findViewById(R.id.layout1);
        minimalLayout = findViewById(R.id.minimalLayout);
        sensorLayout = findViewById(R.id.sensorLayout);
        xboxLayout = findViewById(R.id.xboxLayout);


        layout1.setOnClickListener(v -> {
            Log.d("layout", " layout1");
            Intent layout = new Intent(ViewLayout.this, Layout.class);
            startActivity(layout);
        });

        minimalLayout.setOnClickListener(v -> {
            Log.d("TAG", " minimalLayout");
            Intent layout = new Intent(ViewLayout.this, MinimalLayout.class);
            startActivity(layout);
        });
        xboxLayout.setOnClickListener(v -> {
            Log.d("TAG", " minimalLayout");
            Intent layout = new Intent(ViewLayout.this, XboxLayout.class);
            startActivity(layout);
        });

        sensorLayout.setOnClickListener(v -> {
            Log.d("TAG", " sensorLayout");
            Intent layout = new Intent(ViewLayout.this, CalibrationLayout.class);
            startActivity(layout);
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
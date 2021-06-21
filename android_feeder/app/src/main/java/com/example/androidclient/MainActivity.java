package com.example.androidclient;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.androidclient.configs.Connection;


@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    EditText etIP, etPort;
    Spinner user;
    TextView tvMessages;
    Button btnConnect;
    ImageView layout1, minimalLayout, sensorLayout , xboxLayout;
    String SERVER_IP;
    int SERVER_PORT;
    private String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        user = findViewById(R.id.spinner1);
        tvMessages = findViewById(R.id.tvMessages);
        btnConnect = findViewById(R.id.btnConnect);
        layout1 = findViewById(R.id.layout1);
        minimalLayout = findViewById(R.id.minimalLayout);
        sensorLayout = findViewById(R.id.sensorLayout);
        xboxLayout = findViewById(R.id.xboxLayout);


        layout1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("layout", " layout1");
                connectToServer();
                Intent layout = new Intent(MainActivity.this, Layout.class);
                startActivity(layout);
            }
        });

        minimalLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("TAG", " minimalLayout");
                connectToServer();
                Intent layout = new Intent(MainActivity.this, MinimalLayout.class);
                startActivity(layout);
            }
        });
        xboxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", " minimalLayout");
                connectToServer();
                Intent layout = new Intent(MainActivity.this, XboxLayout.class);
                startActivity(layout);
            }
        });

        sensorLayout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("TAG", " sensorLayout");
                connectToServer();
                Intent layout = new Intent(MainActivity.this, CalibrationLayout.class);
                startActivity(layout);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Connect", "Baba ro Layout ha click kon :/");
            }
        });

    }

    private void connectToServer(){
        tvMessages.setText("");
        SERVER_IP = etIP.getText().toString().trim();
        SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());

        Connection connection = Connection.getInstance();
        connection.createConnection(SERVER_IP, SERVER_PORT);
    }

}


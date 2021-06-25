package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;

import org.json.JSONObject;


@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    EditText etIP, etPort;
    TextView tvMessages;
    Button btnConnect;
    String SERVER_IP;
    int SERVER_PORT;
    boolean isClosedSocket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tvMessages = findViewById(R.id.tvMessages);
        btnConnect = findViewById(R.id.btnConnect);


        Connection connection = Connection.getInstance();
        if(connection.getPort() != 0 ) {
            etIP.setText(connection.getServerIp());
            isClosedSocket = true;
            createRequestThread(0);
            connection.closeConnection();
        }


        btnConnect.setOnClickListener(v -> connectToServer());

    }

    class RequestThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            if (isClosedSocket){
                connection.send("end");
            }else {
                connection.send(Constants.Create_Connection_Message);
                String message;
                message = connection.receive();
                try {
                    JSONObject msg = new JSONObject(message);
                    int port = msg.getInt("port");
                    if (port > 0) {
                        connection.createConnection(SERVER_IP, port);
                    }
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }

        }
    }
    private void goToViewLayout(){
        Intent layout = new Intent(MainActivity.this, ViewLayout.class);
        startActivity(layout);
    }



    private void connectToServer(){
        isClosedSocket = false;
        tvMessages.setText("");
        SERVER_IP = etIP.getText().toString().trim();
        SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
        Connection connection = Connection.getInstance();
        connection.createConnection(SERVER_IP, SERVER_PORT);
        createRequestThread(2000);
        if (connection.getPort() != SERVER_PORT) {
            tvMessages.setText("connected to server successfully");
            goToViewLayout();
            finish();
        } else {
                tvMessages.setText("Error connect to server");
            }
    }
    private void createRequestThread(int timeout){
        Thread requestThread = new Thread(new RequestThread());
        requestThread.start();
        try {
            if (timeout > 0){
                requestThread.join(timeout);
            }else{
                requestThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
package com.example.androidclient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.androidclient.configs.Connection;
import com.example.androidclient.configs.Constants;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    EditText etIP, etPort;
    TextView tvMessages;
    Button btnConnect, btnClose;
    String SERVER_IP;
    int SERVER_PORT;
    Thread requestThread;

    private static final String PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static boolean validateIP(final String ip){
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        etIP = findViewById(R.id.etIP);
        etPort = findViewById(R.id.etPort);
        tvMessages = findViewById(R.id.tvMessages);
        btnConnect = findViewById(R.id.btnConnect);
        btnClose = findViewById(R.id.btnClose);



        btnConnect.setOnClickListener(v -> connectToServer());
        btnClose.setOnClickListener(v -> {
            finish();
            System.exit(0);
        });

    }

    class RequestConnectionThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            connection.createConnection(SERVER_IP, SERVER_PORT);
            connection.send(Constants.Create_Connection_Message);
            String message;
            message = connection.receive();
            try {
                JSONObject msg = new JSONObject(message);
                int port = msg.getInt(Constants.Reply_Message_Port);
                if (port > 0) {
                    connection.setIndex(msg.getInt(Constants.Reply_Message_Index));
                    connection.createConnection(SERVER_IP, port);
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void goToViewLayout(){
        Intent layout = new Intent(MainActivity.this, ViewLayout.class);
        startActivity(layout);
    }



    private void connectToServer() {
        tvMessages.setText("");
        SERVER_IP = etIP.getText().toString().trim();
        SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());
        Connection connection = Connection.getInstance();
        if (!validateIP(SERVER_IP)){
            tvMessages.setText("Error wrong IP pattern");
            return;
        }
        if(!connection.isIndexed()) {
            String connectionIp = connection.getServerIp();
            if (!connectionIp.equals(SERVER_IP) && !connectionIp.isEmpty()) {
                connection.closeConnection();
            }
            requestThread = new Thread(new RequestConnectionThread());
            requestThread.start();
            try {
                requestThread.join(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (connection.isIndexed()) {
            tvMessages.setText("connected to server successfully");
            goToViewLayout();
            disableEditText(etIP);
            disableEditText(etPort);
        } else {
            tvMessages.setText("Error connect to server");
            if (Thread.activeCount() != 0){
                requestThread.interrupt();
                requestThread = null;
            }

        }

    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Connection.getInstance().closeConnection();

    }
}
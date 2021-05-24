package com.example.androidclient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.androidclient.connection.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;


@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    Thread Thread1 = null;
    EditText etIP, etPort;
    Spinner user;
    TextView tvMessages;
    Button btnConnect;
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

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvMessages.setText("");
                SERVER_IP = etIP.getText().toString().trim();
                SERVER_PORT = Integer.parseInt(etPort.getText().toString().trim());

                Connection connection = Connection.getInstance();
                connection.createConnection(SERVER_IP, SERVER_PORT);

                new Thread(new Thread2()).start();
                new Thread(new Thread3()).start();
            }
        });

    }
    private PrintWriter output;
    private BufferedReader input;


    class Thread1 implements Runnable { //establish connection
        public void run() {

//            Socket socket;
//            try {
//
//                socket = new Socket(SERVER_IP, SERVER_PORT);
//                output = new PrintWriter(socket.getOutputStream());
//                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//                String userSelected = String.valueOf(user.getSelectedItem());
//                output.flush();
//                output.write("@@"+ userSelected);
////                output.flush();
//
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tvMessages.setText("Connected\n");
//                    }
//                });
//
//                new Thread(new Thread2()).start();
//
////                String message = "123";
////                new Thread(new Thread3()).start();
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
    class Thread2 implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            String message = "";
            do {
                message = connection.receive();
            }while (!message.equals("bye"));
        }
    }

    class Thread3 implements Runnable { // send data to server
        private Integer counter = 0;

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          counter++;
                                          connection.send(counter.toString());
                                      }
                                  },
                    0, 100);


        }
    }
}


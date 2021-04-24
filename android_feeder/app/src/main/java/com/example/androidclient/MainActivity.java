package com.example.androidclient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
                Thread1 = new Thread(new Thread1());
                Thread1.start();
            }
        });

    }
    private PrintWriter output;
    private BufferedReader input;

    class Thread1 implements Runnable {
        public void run() {
            Socket socket;
            try {
                socket = new Socket(SERVER_IP, SERVER_PORT);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String userSelected = String.valueOf(user.getSelectedItem());
                output.flush();
                output.write("@@"+ userSelected);
                output.flush();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvMessages.setText("Connected\n");
                    }
                });

                new Thread(new Thread2()).start();

                String message = "ADC";
                new Thread(new Thread3(message)).start();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable {
        @Override
        public void run() {
            do {
                try {
                    if (!input.ready()){
                        if (message != null) {
                            runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvMessages.append("server: " + message + "\n");
                                message ="";
                            }
                            });
                        }
                    }
                    int num = input.read();
                    message += Character.toString((char) num);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while (!message.equals("bye"));
        }
    }
    class Thread3 implements Runnable {
        private String message;
        Thread3(String message) {
            this.message = message;
        }
        @Override
        public void run() {

            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          output.flush();
                                          output.write(message);
                                          output.flush();
                                      }
                                  },
                    0, 500);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tvMessages.append("client: " + message + "\n");
                }
            });
        }
    }
}


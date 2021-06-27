package com.example.androidclient.configs;


import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection {
    private static Connection connection = null;
    private DatagramSocket udpSocket;
    private InetAddress serverAddr;
    private int port;
    private String serverIp;

    public static Connection getInstance(){
        if(connection == null){
            connection = new Connection();
        }

        return connection;
    }
    public int getPort(){
        return this.port;
    }
    public String getServerIp(){
        return this.serverIp;
    }

    public void createConnection(String serverIp, int port){
        try {
            this.serverIp = serverIp;
            this.port = port;
            this.udpSocket = new DatagramSocket(port);
            this.serverAddr = InetAddress.getByName(serverIp);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public void send(String message){
        byte[] buf = (message).getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, port);
        try {
            udpSocket.send(packet);

        } catch (IOException e) {
            Log.e("Udp:", "Socket Error:", e);
        }
    }

    public String receive(){
        byte[] receivedMsg = new byte[4096];
        DatagramPacket packet = new DatagramPacket(receivedMsg, receivedMsg.length);
        String receivedMessage = "";

        try {
            udpSocket.receive(packet);
            receivedMessage = new String(receivedMsg, 0, packet.getLength());
            Log.d("message", receivedMessage);
        } catch (SocketException | UnknownHostException e) {
            Log.e("Udp:", "Socket Error:", e);
        } catch (IOException e) {
            Log.e("Udp Receive:", "IO Error:", e);
        }

        return receivedMessage;
    }
    public void closeConnection(){
        if (this.port != 0) {
            Thread requestThread = new Thread(new CloseConnectionThread());
            requestThread.start();
            try {
                requestThread.join(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.port = 0;
            this.udpSocket.close();
            connection = null;
        }
    }

    class CloseConnectionThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
                connection.send("end");
                String message ="";
            do {
                message = connection.receive();
            }while (!message.equals("bye"));
        }
    }


}

package com.example.androidclient.connection;


import android.util.Log;

import org.json.JSONObject;

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
        byte[] buf = (message.toString()).getBytes();

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


}

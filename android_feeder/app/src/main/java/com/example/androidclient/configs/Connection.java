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
    private static int index = -1;
    private DatagramSocket udpSocket = null;
    private InetAddress serverAddr;
    private int port = 0;
    private String serverIp = "";

    public String getServerIp(){
        return this.serverIp;
    }
    public static Connection getInstance(){
        if(connection == null){
            connection = new Connection();
        }

        return connection;
    }

    public boolean isIndexed(){
        return index > -1;
    }
    public void setIndex(int indexOfConnection){
        index = indexOfConnection;
    }

    public void createConnection(String serverIp, int port){
        try {
            this.port = port;
            this.serverIp = serverIp;
            this.udpSocket = new DatagramSocket(port);
            this.serverAddr = InetAddress.getByName(serverIp);
        } catch (Exception eٍٍ) {
            Log.d("Socket Error" ,"something went wrong");
        }
    }


    public void send(String message){
        byte[] buf = (message).getBytes();

        DatagramPacket packet = new DatagramPacket(buf, buf.length, serverAddr, port);
        try {
            udpSocket.send(packet);

        } catch (IOException e) {
            Log.e("Udp:", "Socket Error:", e);
        }catch (Exception eٍٍ) {
            Log.d("Send Error" ,"something went wrong");

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
        }catch (Exception eٍٍ) {
            Log.d("Receive Error" ,"something went wrong");
        }

        return receivedMessage;
    }
    public void closeConnection(){
        if (isIndexed()) {
            Thread requestThread = new Thread(new CloseConnectionThread());
            requestThread.start();
            try {
                requestThread.join(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (this.udpSocket != null){
            this.port = 0;
            this.udpSocket.close();
        }
        connection = null;
    }

    class CloseConnectionThread implements Runnable {

        @Override
        public void run() {
            Connection connection = Connection.getInstance();
            connection.send(Constants.End_Connection_Message);
            String message ="";
            do {
                message = connection.receive();
            }while (!message.equals(Constants.End_Connection_Reply_Message) && !connection.udpSocket.isClosed());
        }
    }


}

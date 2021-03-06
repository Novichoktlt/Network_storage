package org.e.client.service.impl;

import org.e.client.service.NetworkService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class IONetworkService implements NetworkService {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;
    private static IONetworkService instance;

    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;

    private IONetworkService() {}

    public  static IONetworkService getInstance() {
        if(instance == null) {
            instance = new IONetworkService();
            initializeSocket();
            initializeIOStreams();
        }
        return instance;
    }

    private static void initializeSocket() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void initializeIOStreams() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void textCommand(String command) {
        try {
            out.writeUTF(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readCommandResult() {
        try {
            return in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Результат при чтении команды" + e.getMessage());
        }
    }

    @Override
    public void closeConnection() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

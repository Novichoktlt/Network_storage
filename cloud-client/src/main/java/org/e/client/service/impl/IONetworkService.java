package org.e.client.service.impl;

import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import org.e.client.service.NetworkService;
import org.e.domain.Command;


import java.io.IOException;
import java.net.Socket;


public class IONetworkService implements NetworkService {

    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 8189;
    private static IONetworkService instance;

    private static Socket socket;
    private static ObjectDecoderInputStream in;
    private static ObjectEncoderOutputStream out;

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
            in = new ObjectDecoderInputStream(socket.getInputStream());
            out = new ObjectEncoderOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void textCommandClient(Command command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void textCommandCloud(Command command) {
        try {
            out.writeObject(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readCommandResult() {
        try {

            return (String) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Результат при чтении команды " + e.getMessage());
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

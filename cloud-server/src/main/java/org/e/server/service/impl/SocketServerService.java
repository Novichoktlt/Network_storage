package org.e.server.service.impl;

import org.e.server.factory.Factory;
import org.e.server.service.ServerService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerService implements ServerService {

    private static final int SERVER_PORT = 8189;

    private static SocketServerService instance;

    public static SocketServerService getInstance() {
        if(instance == null){
            instance = new SocketServerService();

        }

        return  instance;
    }

    @Override
    public void startServer() {
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Сервер запущен порт =" + SERVER_PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Factory.getClientService(clientSocket).startIOProcess();
                System.out.println("Подключился клиент");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

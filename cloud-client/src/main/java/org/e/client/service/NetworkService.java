package org.e.client.service;

public interface NetworkService {

    void textCommand(String command);

    String readCommandResult();

    void closeConnection();

}

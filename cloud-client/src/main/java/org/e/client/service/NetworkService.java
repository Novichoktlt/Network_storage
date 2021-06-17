package org.e.client.service;

public interface NetworkService {

    void textCommandClient(String command);
    void textCommandCloud(String command);


    String readCommandResult();

    void closeConnection();

}

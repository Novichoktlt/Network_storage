package org.e.client.service;

import org.e.domain.Command;

public interface NetworkService {

    void textCommandClient(Command command);
    void textCommandCloud(Command command);


    String readCommandResult();

    void closeConnection();

}

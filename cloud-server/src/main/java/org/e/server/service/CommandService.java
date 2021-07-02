package org.e.server.service;

import org.e.domain.Command;


public interface CommandService {

    String processCommand(Command command);


    String getCommand();

}

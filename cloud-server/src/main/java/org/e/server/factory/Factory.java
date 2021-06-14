package org.e.server.factory;

import org.e.server.service.ClientService;
import org.e.server.service.CommandDictionaryService;
import org.e.server.service.CommandService;
import org.e.server.service.ServerService;
import org.e.server.service.impl.CommandDictionaryServiceImpl;
import org.e.server.service.impl.IOClientService;
import org.e.server.service.impl.SocketServerService;
import org.e.server.service.impl.command.ViewFilesInDirCommand;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class Factory {

    public  static ServerService getServerService() {
        return SocketServerService.getInstance();
    }

    public  static ClientService getClientService(Socket socket) {

        return new IOClientService(socket);
    }

    public static CommandDictionaryService getCommandDictionaryService() {

        return new CommandDictionaryServiceImpl();
    }

    public static List<CommandService> getCommandService() {

        return Arrays.asList(new ViewFilesInDirCommand());
    }

}

package org.e.server.service.impl;


import org.e.domain.Command;
import org.e.server.factory.Factory;
import org.e.server.service.CommandDictionaryService;
import org.e.server.service.CommandService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandDictionaryServiceImpl implements CommandDictionaryService {

    private final Map<String, CommandService> commandDictionary;

    public CommandDictionaryServiceImpl() {
        this.commandDictionary = Collections.unmodifiableMap(getCommandDictionary());
    }

    private Map<String, CommandService> getCommandDictionary() {

        List<CommandService> commandServiceList = Factory.getCommandService();

        Map<String, CommandService> commandDictionary = new HashMap<>();
        for (CommandService commandService : commandServiceList) {
            commandDictionary.put(commandService.getCommand(), commandService);
        }
        return  commandDictionary;
    }

    @Override
    public String processCommand(Command command) {

        if(commandDictionary.containsKey(command.getCommandName())) {
            return commandDictionary.get(command.getCommandName()).processCommand(command);
        }

        return "Ошибочная команда";
    }
}

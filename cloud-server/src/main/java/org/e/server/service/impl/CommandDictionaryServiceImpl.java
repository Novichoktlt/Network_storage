package org.e.server.service.impl;

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
    public String processCommand(String command) {
        String[] commandParts = command.split("\\s");

        if(commandParts.length > 0 && commandDictionary.containsKey(commandParts[0])) {
            return commandDictionary.get(commandParts[0]).processCommand(command);
        }

        return "Ошбочная команда";
    }
}

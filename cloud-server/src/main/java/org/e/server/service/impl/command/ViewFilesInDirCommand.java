package org.e.server.service.impl.command;

import org.e.domain.Command;
import org.e.server.service.CommandService;

import java.io.File;

public class ViewFilesInDirCommand implements CommandService {
    @Override
    public String processCommand(Command command) {

        final int requirementCountArgs = 1;


        if (command.getArgs().length != requirementCountArgs) {
            throw new IllegalArgumentException("Команда " + getCommand() + " не правельная");
        }

        return process(command.getArgs()[0]);
    }

    private String process(String dirPath) {
        File directory = new File(dirPath);

        if(!directory.exists()){
            return "Дириктория не существует";
        }

        StringBuilder builder = new StringBuilder();
        for (File childFile : directory.listFiles()) {
            String typeFile = getTypeFile(childFile);
            builder.append(childFile.getName()).append(" | ").append(typeFile).append(System.lineSeparator());
        }
        return builder.toString();
    }

    private String getTypeFile(File childFile) {
        return childFile.isDirectory() ? "DIR" : "FILE";
    }

    @Override
    public String getCommand() {
        return "ls";
    }
}

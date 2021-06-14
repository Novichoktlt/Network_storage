package org.e.server.service.impl.command;

import org.e.server.service.CommandService;

import java.io.File;

public class ViewFilesInDirCommand implements CommandService {
    @Override
    public String processCommand(String command) {

        final  int requirementCountCommandPart = 2;

        String[] actualCommandParts = command.split("\\s");
        if(actualCommandParts.length != requirementCountCommandPart) {
            throw  new IllegalArgumentException("Команда" + getCommand() + "не правельная");
        }

        return process(actualCommandParts[1]);
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

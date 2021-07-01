package org.e.client.controller;




import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Data


public class FileInfo {

    public static final String UP = "[ . . ]";

    private String filename;
    private long length;

    public boolean isDirectory(){
        return length == -1L;
    }

    public boolean isUpElement(){
        return length == -2L;
    }

    public FileInfo(String filename, long length){
        this.filename = filename;
        this.length = length;
    }

    public FileInfo(Path path) {

            try {
                this.filename = path.getFileName().toString();
                if (Files.isDirectory(path)) {
                    this.length = -1L;
                } else {
                    this.length = Files.size(path);
                }
            } catch (IOException e) {
                throw new RuntimeException("Что-то не так с файлом " + path.toAbsolutePath().toString());
            }

    }

}

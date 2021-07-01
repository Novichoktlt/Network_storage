package org.e.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.e.client.factory.Factory;
import org.e.client.service.NetworkService;
import org.e.domain.Command;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    public static int c = 0;
    private NetworkService networkService;
    Path root;

    @FXML
    public TextField commandTextFieldClient;
    public TextField commandTextFieldCloud;
    public TextArea commandTextAreaClient;
    public ListView<FileInfo> commandListViewCloud;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        commandListViewCloud.setCellFactory(new Callback<ListView<FileInfo>, ListCell<FileInfo>>() {
            @Override
            public ListCell<FileInfo> call(ListView<FileInfo> param) {
                return new ListCell<FileInfo>(){
                    @Override
                    protected void updateItem(FileInfo item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty){
                            setText(null);
                            setStyle("");
                        } else {
                            String formattedFilename = String.format("%-30s", item.getFilename());
                            String formattedFiles;
                            if (item.getLength() == -1L) {
                               formattedFiles = String.format("%s", "[ DIR ]");
                            } else {
                                formattedFiles = String.format("%s", "[ Fil ]");
                            }
                            if (item.getLength() == -2L) {
                                formattedFiles = "";
                            }
                            String text = String.format("%s %20s", formattedFiles, formattedFilename);
                            setText(text);
                        }
                    }
                };
            }
        });
        goToPath(Paths.get("C:\\Users"));
        networkService = Factory.getNetworkService();
        createCommandResultHandler();
    }

    private void createCommandResultHandler(){
        new Thread(() -> {
            while (true){
                String resultCommand = networkService.readCommandResult();
                if (c == 1){

//                    Platform.runLater(() -> commandListViewCloud.appendText(resultCommand + System.lineSeparator()));
                }
                if (c == 2) {

                    Platform.runLater(() -> commandTextAreaClient.appendText(resultCommand + System.lineSeparator()));
                }
                c = 0;
            }
        }).start();

    }

    public void textCommandCloud(ActionEvent event) {
//        c = 1;
//        String[] textCommand = commandListViewCloud.getText().trim().split("\\s", 2);
//
//        if (textCommand.length > 1){
//            String[] commandArgs = Arrays.copyOfRange(textCommand, 1, textCommand.length);
//            networkService.textCommandCloud(new Command(textCommand[0], commandArgs));
//            commandTextAreaCloud.clear();
//        }

    }

    public void textCommandClient(ActionEvent event) {
        c = 2;

        String[] textCommand = commandTextFieldClient.getText().trim().split("\\s", 2);

        if (textCommand.length > 1) {
            String[] commandArgs = Arrays.copyOfRange(textCommand, 1, textCommand.length);
            networkService.textCommandClient(new Command(textCommand[0], commandArgs));
            commandTextFieldClient.clear();
        }
    }

    public void shutdown() {
        networkService.closeConnection();
    }

    public void goToPath(Path path){
        root = path;
        commandTextFieldCloud.setText(root.toAbsolutePath().toString());
        commandListViewCloud.getItems().clear();
        if (root.toAbsolutePath().getParent() != null){
            commandListViewCloud.getItems().add(new FileInfo(FileInfo.UP, -2L));
        }

        commandListViewCloud.getItems().addAll(scanFiles(path));
        commandListViewCloud.getItems().sort((o1, o2) -> {

            if (o1.getFilename().equals(FileInfo.UP))
                return  -1;
            if ((int)Math.signum(o1.getLength()) == (int)Math.signum(o2.getLength())){
                return o1.getFilename().compareTo(o2.getFilename());
            }
            return Long.valueOf(o1.getLength() - o2.getLength()).intValue();
        });

    }

    public List<FileInfo> scanFiles(Path root){
        try {
            return Files.list(root).map(FileInfo::new).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка файла " + root);
        }
    }

    public void filesListClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            FileInfo fileInfo = commandListViewCloud.getSelectionModel().getSelectedItem();
            if (fileInfo != null){
                if (fileInfo.isDirectory()){
                    Path pathTo = root.resolve(fileInfo.getFilename());
                    goToPath(pathTo);
                }
                if (fileInfo.isUpElement()) {
                    Path pathTo = root.toAbsolutePath().getParent();
                    goToPath(pathTo);
                }
            }
        }
    }
}



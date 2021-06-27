package org.e.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.e.client.factory.Factory;
import org.e.client.service.NetworkService;
import org.e.domain.Command;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField commandTextFieldClient;
    public TextField commandTextFieldCloud;
    public TextArea commandTextAreaClient;
    public TextArea commandTextAreaCloud;
    public static int c = 0;
    private NetworkService networkService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        networkService = Factory.getNetworkService();
        createCommandResultHandler();
    }

    private void createCommandResultHandler(){
        new Thread(() -> {
            while (true){
                String resultCommand = networkService.readCommandResult();
                if (c == 1){

                    Platform.runLater(() -> commandTextAreaCloud.appendText(resultCommand + System.lineSeparator()));
                }
                if (c == 2) {

                    Platform.runLater(() -> commandTextAreaClient.appendText(resultCommand + System.lineSeparator()));
                }
                c = 0;
            }
        }).start();

    }

    public void textCommandCloud(ActionEvent event) {
        c = 1;
        String[] textCommand = commandTextFieldCloud.getText().trim().split("\\s", 2);

        if (textCommand.length > 1){
            String[] commandArgs = Arrays.copyOfRange(textCommand, 1, textCommand.length);
            networkService.textCommandCloud(new Command(textCommand[0], commandArgs));
            commandTextAreaCloud.clear();
        }

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
}

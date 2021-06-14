package org.e.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.e.client.factory.Factory;
import org.e.client.service.NetworkService;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public TextField commandTextFieldClient;
    public TextArea commandResultTextAreaClient;

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
                Platform.runLater(() -> commandResultTextAreaClient.appendText(resultCommand + System.lineSeparator()));
            }
        }).start();
    }

    public void textCommand(ActionEvent event) {
        networkService.textCommand(commandTextFieldClient.getText().trim());
//        commandTextFieldClient.clear();

    }

    public void shutdown() {
        networkService.closeConnection();
    }
}

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
        networkService.textCommandCloud(commandTextFieldCloud.getText().trim());
        commandTextAreaCloud.clear();

    }

    public void textCommandClient(ActionEvent event) {
        c = 2;
        networkService.textCommandClient(commandTextFieldClient.getText().trim());
        commandTextAreaClient.clear();

    }

    public void shutdown() {
        networkService.closeConnection();
    }
}

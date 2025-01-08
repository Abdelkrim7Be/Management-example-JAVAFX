package com.abdelkrim.management_template.presentation.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/abdelkrim/management_template/views/dashboard.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("FXML file not found: /com/abdelkrim/management_template/views/dashboard.fxml");
        }
        Parent root = loader.load();

        primaryStage.setTitle("Employee Management Application");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}

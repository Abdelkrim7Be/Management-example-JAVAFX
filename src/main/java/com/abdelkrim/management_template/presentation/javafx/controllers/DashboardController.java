package com.abdelkrim.management_template.presentation.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML
    private VBox centerPane;

    @FXML
    private void handleEmployeeDashboard(ActionEvent event) {
        loadDashboard("employe.fxml");
    }

    @FXML
    private void handleEnterpriseDashboard(ActionEvent event) {
        loadDashboard("entreprise.fxml");
    }

    @FXML
    private void handleDepartmentDashboard(ActionEvent event) {
        loadDashboard("departement.fxml");
    }


    private void loadDashboard(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/abdelkrim/management_template/views/" + fxmlFile));
            BorderPane pane = loader.load(); // Change AnchorPane to BorderPane if the root element in FXML is BorderPane

            centerPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

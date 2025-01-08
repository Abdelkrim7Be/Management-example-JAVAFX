package com.abdelkrim.management_template.presentation.javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane centerPane;

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
            AnchorPane pane = loader.load();

            centerPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

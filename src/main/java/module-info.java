module com.abdelkrim.management_template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.abdelkrim.management_template.dao;
    exports com.abdelkrim.management_template.presentation.models;
    exports com.abdelkrim.management_template.presentation.javafx.controllers;
    exports com.abdelkrim.management_template.presentation.javafx to javafx.graphics;

    opens com.abdelkrim.management_template.presentation.javafx.controllers to javafx.fxml; // Add this line
    opens com.abdelkrim.management_template.views to javafx.fxml;

}
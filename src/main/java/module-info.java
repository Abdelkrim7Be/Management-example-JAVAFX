module com.abdelkrim.management_template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.abdelkrim.management_template.dao;
    exports com.abdelkrim.management_template.models;
    exports com.abdelkrim.management_template.presentation.javafx.controllers;

    opens com.abdelkrim.management_template.views to javafx.fxml;
}
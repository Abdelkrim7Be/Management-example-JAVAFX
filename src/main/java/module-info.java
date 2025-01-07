module com.abdelkrim.management_template {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

// Export the packages to be used by other modules
    exports com.abdelkrim.management_template.dao;
    exports com.abdelkrim.management_template.models;
    exports com.abdelkrim.management_template.presentation.javafx;
    exports com.abdelkrim.management_template.presentation.javafx.controllers;

    // Open the package for reflection (needed by JavaFX FXML)
    opens com.abdelkrim.management_template.presentation.javafx.views to javafx.fxml;
    opens com.abdelkrim.management_template.presentation.javafx.controllers to javafx.fxml;
    opens com.abdelkrim.management_template.models to javafx.base;
}
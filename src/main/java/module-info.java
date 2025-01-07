module com.abdelkrim.management_template {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.abdelkrim.management_template to javafx.fxml;
    exports com.abdelkrim.management_template;
}
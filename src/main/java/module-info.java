module org.example.backery_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires com.google.protobuf;
    requires mysql.connector.j;
    requires java.mail;

    //opens lk.ijse.bakerymanagment to javafx.fxml;
    opens lk.ijse.backery_management_system.controller to javafx.fxml;
    opens lk.ijse.backery_management_system.viewTm to javafx.base;
    opens lk.ijse.backery_management_system.dto to javafx.base;

    exports lk.ijse.backery_management_system;
}
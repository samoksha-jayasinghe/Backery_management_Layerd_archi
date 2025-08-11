module org.example.backery_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;


    opens lk.ijse.backery_management_system to javafx.fxml;
    exports lk.ijse.backery_management_system;
}
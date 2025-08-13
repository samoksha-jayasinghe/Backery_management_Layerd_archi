package lk.ijse.backery_management_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;


public class AppInitializer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/StartPage.fxml"));


        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        //primaryStage.setResizable(false); //true
        primaryStage.setTitle("S & J Bakery Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
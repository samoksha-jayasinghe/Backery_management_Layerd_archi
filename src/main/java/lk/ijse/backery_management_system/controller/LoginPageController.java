package lk.ijse.backery_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bakerymanagment.util.CrudUtil;
import javafx.application.Platform;


import java.sql.ResultSet;
import java.sql.SQLXML;

public class LoginPageController {


    public AnchorPane ancSignin;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Button btnSignIn;
    @FXML
    private AnchorPane loginAnc;

    private final String userNamePattern = "^[A-Za-z0-9_]{3,}$";
    private final String passwordPattern = "^[A-Za-z0-9@#$%^&+=]{6,}$";
    private SQLXML actionEvent;


    public void initialize() {
        // Add listeners to validate fields in real-time
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> validateFields());

        // Disable login button initially
        btnSignIn.setDisable(true);
    }

    private void validateFields() {
        boolean isValidUsername = txtUsername.getText().matches(userNamePattern);
        boolean isValidPassword = txtPassword.getText().matches(passwordPattern);

        // Reset styles
        txtUsername.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        txtPassword.setStyle("-fx-border-color: #7367F0; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        // Apply red border if invalid
        if (!isValidUsername)
            txtUsername.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");
        if (!isValidPassword)
            txtPassword.setStyle("-fx-border-color: red; -fx-border-radius: 12px; -fx-background-radius: 12px;");

        // Enable or disable login button
        btnSignIn.setDisable(!(isValidUsername && isValidPassword));
    }


    public void goToForgetPassword(MouseEvent mouseEvent) {
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {
        String inputUserName = txtUsername.getText();
        String inputPassword = txtPassword.getText();
        // Here you would typically check the credentials against a database or other data source

        if (inputUserName.isEmpty() || inputPassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please enter username and password").show();
            return;
        }

        try {
            ResultSet resultSet = CrudUtil.execute(
                    "SELECT * FROM users WHERE name = ? AND password = ?",
                    inputUserName, inputPassword);
            if (resultSet.next()) {
                try {
                    /*Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/DashBoardView.fxml"));
                    Stage dashBoardStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Stage dashBoardStage = (Stage) this.loginAnc.getScene().getWindow();
                    dashBoardStage.setScene(new Scene(dashBoardRoot));
                    dashBoardStage.setTitle("Dream Baby Care Products");
                    dashBoardStage.setResizable(true);
                    // Fix: Delay maximization until after rendering
                    Platform.runLater(() -> {
                        dashBoardStage.setMaximized(true);
                    });
                    dashBoardStage.show();*/
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBordPage.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
                    stage.setScene(scene);
                    stage.setMaximized(true);
                    stage.setTitle("S & J Bakery Management System");
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Failed to load to dash board").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid username or password").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Login failed. Database Connection Error").show();

        }
        /*if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "User has been created").show();

            String subject = "Welcome to Alpha Modifications";
            String message = "Dear " + inputUsername + ",\n\n" +
                    "Thank you for signing up with Alpha Modifications. Your account has been successfully created.\n\n" +
                    "Best regards,\n" +
                    "Alpha Modifications Team";

            EmailUtil.sendEmail(inputEmail , subject , message);

            btnSignInOnAction(actionEvent);
        }else {
            new Alert(Alert.AlertType.ERROR, "User has not been saved").show();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
        }
    }*/
    }






    public void goToSignUpPage(MouseEvent mouseEvent) {
        try {
            Parent dashBoardRoot = FXMLLoader.load(getClass().getResource("/view/SignupPage.fxml"));
            Stage dashBoardStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            dashBoardStage.setScene(new Scene(dashBoardRoot));
            dashBoardStage.setTitle("Dream Baby Care Products");
            dashBoardStage.setResizable(true);
            dashBoardStage.setMaximized(true); // Maximizes the window with window controls
            dashBoardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load to SignUp page").show();
        }
    }
    }



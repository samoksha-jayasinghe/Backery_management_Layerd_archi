package lk.ijse.backery_management_system.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bakerymanagment.dto.UsersDto;
import lk.ijse.bakerymanagment.model.UserModel;
import lk.ijse.bakerymanagment.util.CrudUtil;

import java.sql.ResultSet;
import java.util.UUID;

public class SignUpControllerPage {
    public AnchorPane ancSignup;
    public AnchorPane ancSignUpPage;

    public TextField txtUsername;
    public TextField txtEmail;
    public TextField txtAddress;
    public TextField txtContact;
    public TextField txtRole;

    public PasswordField txtPassword;
    public PasswordField txtRePassword;

    public Button btnSignIn;
    public Button btnSignup;
    public Button btnSignUp;

    public Label txtSignIn;
    public Label txtGoSignIn;

    //
    //
    // UsersDto userDto = new UsersDto(userId, name, addredd, email, contact, password );


    String userId = UUID.randomUUID().toString(); // Import java.util.UUID


    private final UserModel userModel = new UserModel();

    public void initialize() {
        // No validation needed
    }

    public void btnSignUpOnAction(ActionEvent actionEvent) {
        String inputUsername = txtUsername.getText();
        String inputEmail = txtEmail.getText();
        String inputAddress = txtAddress.getText();
        String inputContact = txtContact.getText();
        String inputPassword = txtPassword.getText();
        String inputRePassword = txtRePassword.getText();

        if (inputUsername.isEmpty() || inputEmail.isEmpty() || inputContact.isEmpty() ||
                inputAddress.isEmpty() || inputPassword.isEmpty() || inputRePassword.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please fill all the fields").show();
            return;
        }

        if (!inputPassword.equals(inputRePassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match").show();
            return;
        }

        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM users WHERE name = ? OR email = ?", inputUsername, inputEmail);

            if (resultSet.next()) {
                new Alert(Alert.AlertType.ERROR, "User already exists").show();
                return;
            }

            String userId = UserModel.getNextUserId();

            boolean isSaved = UserModel.saveUser(new UsersDto(
                    userId,
                    inputUsername,
                    inputAddress,
                    inputEmail,
                    inputContact,
                    inputPassword
            ));

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User has been created").show();

                String subject = "Welcome to S&J Bakers";
                String message = "Dear " + inputUsername + ",\n\n" +
                        "Thank you for signing up with S&J Bakers. Your account has been successfully created.\n\n" +
                        "Best regards,\nS&J Bakers Team";

                EmailUtil.sendEmail(inputEmail, subject, message);

                navigateTo("/view/LoginPage.fxml");
            } else {
                new Alert(Alert.AlertType.ERROR, "User has not been saved").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Sign Up Failed").show();
        }
    }

    public void onClickedtxtGoSignIn(MouseEvent mouseEvent) {
        navigateTo("/view/LoginPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancSignup.getChildren().clear();
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));
            anchorPane.prefWidthProperty().bind(ancSignup.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSignup.heightProperty());
            ancSignup.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            e.printStackTrace();
        }
    }
}
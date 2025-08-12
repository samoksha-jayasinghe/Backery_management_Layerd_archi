package lk.ijse.backery_management_system.controller;



import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.backery_management_system.bo.BOFactory;
import lk.ijse.backery_management_system.bo.custom.SupplierBO;
import lk.ijse.backery_management_system.bo.custom.UserBO;
import lk.ijse.backery_management_system.dto.UsersDto;
import lk.ijse.backery_management_system.viewTm.UserTm;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    public AnchorPane ancUser;
    public Text lblUserId;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtContact;

    public TableView<UserTm> tblUser;
    public TableColumn<UserTm, String> colUserId;
    public TableColumn<UserTm, String> colName;
    public TableColumn<UserTm, String> colAddress;
    public TableColumn<UserTm, String> colEmail;
    public TableColumn<UserTm, String> colContact;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button txtBack;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USERS);

    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public TextField txtSearch;
    public TextField txtPassword;
    public TableColumn colPassword;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        try {
            resetPage();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }

    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        tblUser.setItems(FXCollections.observableArrayList(
                userBO.getAll().stream()
                        .map(usersDto -> new UserTm(
                                usersDto.getUserId(),
                                usersDto.getName(),
                                usersDto.getAddress(),
                                usersDto.getEmail(),
                                usersDto.getContact(),
                                usersDto.getPassword()
                        )).toList()
        ));
    }


    private void resetPage() {
        try {
            loadTable();
           // loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtAddress.setText(null);
            txtEmail.setText(null);
            txtContact.setText(null);
            txtPassword.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

   /* private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = userModel.getNextUserId();
        lblUserId.setText(nextId);
    }*/


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String password = txtPassword.getText();

        int txtPassword = Integer.parseInt(password);

        boolean emailValid = email.matches(emailPattern);
        boolean contactValid = contact.length() == 10;

        ;

        if (emailValid && contactValid) {
            try {
                boolean isSaved = userBO.save(new UsersDto(
                        userId,
                        name,
                        address,
                        email,
                        contact,
                        txtPassword
                ));
                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User Saved", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String userId = lblUserId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String password = txtPassword.getText();

        int txtPassword = Integer.parseInt(password);

        boolean emailValid = email.matches(emailPattern);
        boolean contactValid = contact.length() == 10;

        ;

        if (emailValid && contactValid) {
            try {
                boolean isUpdated = userBO.update(new UsersDto(
                        userId,
                        name,
                        address,
                        email,
                        contact,
                        txtPassword
                ));
                if (isUpdated) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User Saved", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u sure ?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String userId = lblUserId.getText();

            try {
                boolean isDeleted = userBO.delete(userId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "User Deleted", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }


    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try {
        ancUser.getChildren().clear();

        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

        anchorPane.prefWidthProperty().bind(ancUser.widthProperty());
        anchorPane.prefHeightProperty().bind(ancUser.heightProperty());

        ancUser.getChildren().setAll(anchorPane);


    }catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something error").show();
            e.printStackTrace();

        }
}

    public void onClickedTable(MouseEvent mouseEvent) {
        UserTm selectedUser = tblUser.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            lblUserId.setText(selectedUser.getUserId());
            txtName.setText(selectedUser.getName());
            txtAddress.setText(selectedUser.getAddress());
            txtEmail.setText(selectedUser.getEmail());
            txtContact.setText(selectedUser.getContact());
            txtPassword.setText(String.valueOf(selectedUser.getPassword()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


}

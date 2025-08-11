package lk.ijse.backery_management_system.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.backery_management_system.dto.CustomerDto;
//import lk.ijse.backery_management_system.dto.tm.CustomerTM;
//import lk.ijse.backery_management_system.model.CustomerModel;
import lk.ijse.backery_management_system.viewTm.CustomerTM;
import lk.ijse.backery_management_system.dao.custom.CustomerDAO;
import lk.ijse.backery_management_system.dao.custom.impl.CustomerDAOImpl;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerPageController implements Initializable {
    public AnchorPane ancCustomer;
    public Text lblCustomerId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtUserId;

    public TableView<CustomerTM> tblCustomer;
    public TableColumn<CustomerTM, String> colId;
    public TableColumn<CustomerTM, String> colName;
    public TableColumn<CustomerTM, String> colContact;
    public TableColumn<CustomerTM, String> colAddress;
    public TableColumn<CustomerTM, String> colEmail;
    public TableColumn<CustomerTM, String> colUserId;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button txtBack;

    //private final CustomerModel customerModel = new CustomerModel();

    private final String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userID"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }

    }

    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblCustomer.setItems(FXCollections.observableArrayList(
                customerModel.getAllCustomer().stream()
                        .map(customerDto -> new CustomerTM(
                                customerDto.getCustomerId(),
                                customerDto.getFirstName(),
                                customerDto.getAddress(),
                                customerDto.getEmail(),
                                customerDto.getContact(),
                                customerDto.getUserID()
                        )).toList()
        ));

    }

    public void resetPage() {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtContact.setText(null);
            txtAddress.setText(null);
            txtEmail.setText(null);
            txtUserId.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();

        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String firstName = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = txtUserId.getText();

        boolean emailValid = email.matches(emailPattern);
        boolean contactValid = contact.length() == 10;

        CustomerDto customerDto = new CustomerDto(
                customerId,
                firstName,
                address,
                email,
                contact,
                userId
        );
        if (emailValid && contactValid) {
            try {
                boolean isSaved = customerModel.saveCustomer(customerDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Saved successfully", ButtonType.OK).show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Saving failed", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
            }

        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String customerId = lblCustomerId.getText();
        String firstName = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        String userId = txtUserId.getText();

        CustomerDto customerDto = new CustomerDto(
                customerId,
                firstName,
                address,
                email,
                contact,
                userId
        );
        try {
            boolean isUpdate = customerModel.updateCustomer(customerDto);
            if (isUpdate) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated successfully", ButtonType.OK).show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Updating failed", ButtonType.OK).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u Sure ?",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String customerId = lblCustomerId.getText();

            try {
                boolean isDeleted = customerModel.deleteCustomer(customerId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Deleting failed", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
            }
        }
    }

    public void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = customerModel.getNextCustomerId();
        lblCustomerId.setText(nextId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    public void onClickTable(MouseEvent mouseEvent) {
        CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            lblCustomerId.setText(selectedCustomer.getCustomerId());
            txtName.setText(selectedCustomer.getFirstName());
            txtAddress.setText(selectedCustomer.getAddress());
            txtEmail.setText(selectedCustomer.getEmail());
            txtContact.setText(selectedCustomer.getContact());
            txtUserId.setText(selectedCustomer.getUserID());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void navigateTo(String path) {
        try {
            ancCustomer.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancCustomer.widthProperty());
            anchorPane.prefHeightProperty().bind(ancCustomer.heightProperty());

            ancCustomer.getChildren().setAll(anchorPane);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }

    public void search(KeyEvent keyEvent) {
        String searchText = txtSearch.getText();
        if (searchText.isEmpty()) {
            try {
                loadTableData();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load customers").show();
            }
        } else {
            try {
                ArrayList<CustomerDto> customerList = customerModel.searchCustomer(searchText);
                tblCustomer.setItems(FXCollections.observableArrayList(
                        customerList.stream()
                                .map(customerDto -> new CustomerTM(
                                        customerDto.getCustomerId(),
                                        customerDto.getFirstName(),
                                        customerDto.getAddress(),
                                        customerDto.getContact(),
                                        customerDto.getEmail(),
                                        customerDto.getUserID()
                                ))
                                .toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search customers").show();
            }
        }


    }
}

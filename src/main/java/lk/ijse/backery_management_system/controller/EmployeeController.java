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
import lk.ijse.bakerymanagment.dto.CustomerDto;
import lk.ijse.bakerymanagment.dto.EmployeeDto;
import lk.ijse.bakerymanagment.dto.tm.EmployeeTM;
import lk.ijse.bakerymanagment.model.EmployeeModel;

import javax.xml.xpath.XPath;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    public AnchorPane ancEmployee;
    public Text lblEmployeeI;
    public TextField txtName;
    public TextField txtRole;
    public TextField txtSalary;
    public TextField txtContact;

    public TableView<EmployeeTM> tblEmployee;
    public TableColumn<EmployeeTM , String> colEmployeeId;
    public TableColumn<EmployeeTM , String> colName;
    public TableColumn<EmployeeTM , String> colRole;
    public TableColumn<EmployeeTM , String> colSalary;
    public TableColumn<EmployeeTM , String> colContact;


    public Button txtBack;
    public Button btnSave;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnReset;

    private EmployeeModel employeeModel = new EmployeeModel();

    public TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something Error",ButtonType.OK).show();
        }
    }
    public void loadTable() throws SQLException, ClassNotFoundException {
        tblEmployee.setItems(FXCollections.observableArrayList(
                employeeModel.getAllEmployee().stream()
                        .map(employeeDto -> new EmployeeTM(
                                employeeDto.getEmployeeId(),
                                employeeDto.getName(),
                                employeeDto.getRole(),
                                employeeDto.getSalary(),
                                employeeDto.getContact()
                        )).toList()
        ));
    }

    private void resetPage() {
        try {
            loadTable();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtName.setText(null);
            txtRole.setText(null);
            txtSalary.setText(null);
            txtContact.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something Error",ButtonType.OK).show();
        }

    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = employeeModel.getNextEmployeeId();
        lblEmployeeI.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String employeeId = lblEmployeeI.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String salary = txtSalary.getText();
        String contact = txtContact.getText();

        boolean contactValid = contact.length() == 10;

        EmployeeDto employeeDto = new EmployeeDto(
                employeeId,
                name,
                role,
                salary,
                contact

        );
        if ( contactValid) {
            try {
                boolean isSaved = employeeModel.saveEmployee(employeeDto);

                if (isSaved) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Saved successfully", ButtonType.OK).show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Saving failed", ButtonType.OK).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
            }

        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String employeeId = lblEmployeeI.getText();
        String name = txtName.getText();
        String role = txtRole.getText();
        String salary = txtSalary.getText();
        String contact = txtContact.getText();

        EmployeeDto employeeDto = new EmployeeDto(
                employeeId,
                name,
                role,
                salary,
                contact
        );
        try {
            boolean isUpdate = employeeModel.updateEmployee(employeeDto);
            if (isUpdate) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Updated successfully", ButtonType.OK).show();

            } else {
                new Alert(Alert.AlertType.ERROR, "Update failed", ButtonType.OK).show();
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
            String employeeId = lblEmployeeI.getText();

            try {
                boolean isDelete = employeeModel.deleteEmployee(employeeId);
                if (isDelete) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully", ButtonType.OK).show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Delete failed", ButtonType.OK).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
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
        try{
            ancEmployee.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancEmployee.widthProperty());
            ancEmployee.prefHeightProperty().bind(anchorPane.heightProperty());

            ancEmployee.getChildren().setAll(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,"Something Error",ButtonType.OK).show();
            e.printStackTrace();
        }
    }



    public void onclickEmployee(MouseEvent mouseEvent) {
        EmployeeTM selectedEmpolyee = tblEmployee.getSelectionModel().getSelectedItem();

        if (selectedEmpolyee != null) {
            lblEmployeeI.setText(selectedEmpolyee.getEmployeeId());
            txtName.setText(selectedEmpolyee.getName());
            txtRole.setText(selectedEmpolyee.getRole());
            txtSalary.setText(selectedEmpolyee.getSalary());
            txtContact.setText(selectedEmpolyee.getContact());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


   /* public void btnSearchOnAction(ActionEvent actionEvent) {
    }*/
}



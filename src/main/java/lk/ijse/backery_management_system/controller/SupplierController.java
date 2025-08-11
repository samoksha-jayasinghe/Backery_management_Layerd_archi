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
import lk.ijse.bakerymanagment.dto.SupplierDto;
import lk.ijse.bakerymanagment.dto.tm.SupplierTM;
import lk.ijse.bakerymanagment.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {

    public AnchorPane ancSupplier;
    public Text lblSupplierId;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtAddress;

    public TableView<SupplierTM> tblSupplier;
    public TableColumn<SupplierTM , String> colSupplierId;
    public TableColumn<SupplierTM , String> colName;
    public TableColumn<SupplierTM , String> colContact;
    public TableColumn<SupplierTM , String> colAddress;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;
    public Button txtBack;

    private final SupplierModel supplierModel = new SupplierModel();

    public TextField txtSearch;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
        }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        tblSupplier.setItems(FXCollections.observableArrayList(
                supplierModel.getAllSupplier().stream()
                        .map(supplierDto -> new SupplierTM(
                                supplierDto.getSupplierId(),
                                supplierDto.getName(),
                                supplierDto.getContact(),
                                supplierDto.getAddress()
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
            txtContact.setText(null);
            txtAddress.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = supplierModel.getNextSupplierId();
        lblSupplierId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String supplierId = lblSupplierId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        SupplierDto supplierDto = new SupplierDto(
                supplierId,
                name,
                contact,
                address
        );
        try {
            boolean isSaved = supplierModel.saveSupplier(supplierDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong", ButtonType.OK);
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String supplierId = lblSupplierId.getText();
        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        SupplierDto supplierDto = new SupplierDto(
                supplierId,
                name,
                contact,
                address
        );
        try {
            boolean isUpdated = supplierModel.updateSupplier(supplierDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION,"Something went wrong", ButtonType.OK);
            }else {
                new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
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
            String supplierId = lblSupplierId.getText();

            try {
                boolean isDeleted = supplierModel.deleteSupplier(supplierId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION,"Something went wrong", ButtonType.OK);
                }else {
                    new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
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
            ancSupplier.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancSupplier.widthProperty());
            anchorPane.prefHeightProperty().bind(ancSupplier.heightProperty());

            ancSupplier.getChildren().setAll(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Something went wrong", ButtonType.OK);
            e.printStackTrace();

        }

    }

    public void onClickedTable(MouseEvent mouseEvent) {
        SupplierTM selectedSupplier = tblSupplier.getSelectionModel().getSelectedItem();

        if (selectedSupplier != null) {
            lblSupplierId.setText(selectedSupplier.getSupplierId());
            txtName.setText(selectedSupplier.getName());
            txtContact.setText(selectedSupplier.getContact());
            txtAddress.setText(selectedSupplier.getAddress());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }


}

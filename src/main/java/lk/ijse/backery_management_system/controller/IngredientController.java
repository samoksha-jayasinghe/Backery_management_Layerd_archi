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
import lk.ijse.bakerymanagment.dto.IngredientDto;
import lk.ijse.bakerymanagment.dto.tm.IngredientTM;
import lk.ijse.bakerymanagment.model.IngredientModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class IngredientController implements Initializable {

    public AnchorPane ancIngredient;

    public Text lblItemId;
    public TextField txtProductId;
    public TextField txtBatchno;
    public TextField txtExpireDate;
    public TextField txtQuantity;
    public TextField txtIngredientName;

    public TableView<IngredientTM> tblIngredient;
    public TableColumn<IngredientTM , String> colItemId;
    public TableColumn<IngredientTM , String> colProductId;
    public TableColumn<IngredientTM , String> colBatchNo;
    public TableColumn<IngredientTM , String> colExpireDate;
    public TableColumn<IngredientTM , String> colQuantity;
    public TableColumn<IngredientTM , String> colIngredientName;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final IngredientModel ingredientModel = new IngredientModel();

    public TextField txtSearch;
    public Button txtBackpage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colBatchNo.setCellValueFactory(new PropertyValueFactory<>("batchno"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colIngredientName.setCellValueFactory(new PropertyValueFactory<>("ingredientName"));

        try {
            resetPage();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }
    public void loadTableData() throws SQLException, ClassNotFoundException {
        tblIngredient.setItems(FXCollections.observableArrayList(
                ingredientModel.getAllIngredient().stream()
                        .map(ingredientDto -> new IngredientTM(
                                ingredientDto.getItemId(),
                                ingredientDto.getProductId(),
                                ingredientDto.getBatchno(),
                                ingredientDto.getDate(),
                                ingredientDto.getQty(),
                                ingredientDto.getIngredientName()
                        )).toList()
        ));
    }

    private void resetPage() throws SQLException, ClassNotFoundException {
        try {
            loadTableData();
            loadNextId();

            btnSave.setDisable(false);
            btnUpdate.setDisable(true);
            btnDelete.setDisable(true);

            txtProductId.setText(null);
            txtBatchno.setText(null);
            txtExpireDate.setText(null);
            txtQuantity.setText(null);
            txtIngredientName.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String itemtId = lblItemId.getText();
        String productId = txtProductId.getText();
        String batchNo = txtBatchno.getText();
        String expireDate = txtExpireDate.getText();
        String quantity = txtQuantity.getText();
        String ingredientName = txtIngredientName.getText();

        int preseQty = Integer.parseInt(quantity);

        IngredientDto ingredientDto = new IngredientDto(
                itemtId,
                productId,
                batchNo,
                expireDate,
                preseQty,
                ingredientName

        );
        try {
            boolean isSaved = ingredientModel.saveIngredient(ingredientDto);

            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Saved successfully", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to save", ButtonType.OK).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to save", ButtonType.OK).show();

        }

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String itemtId = lblItemId.getText();
        String productId = txtProductId.getText();
        String batchNo = txtBatchno.getText();
        String expireDate = txtExpireDate.getText();
        String quantity = txtQuantity.getText();
        String ingredientName = txtIngredientName.getText();

        int preseQty = Integer.parseInt(quantity);

        IngredientDto ingredientDto = new IngredientDto(
                itemtId,
                productId,
                batchNo,
                expireDate,
                preseQty,
                ingredientName

        );
        try {
            boolean isUpdate = ingredientModel.updateIngredient(ingredientDto);

            if (isUpdate) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Update successfully", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update", ButtonType.OK).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to Update", ButtonType.OK).show();

        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are u sure ? ",
                ButtonType.YES,
                ButtonType.NO
        );
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String itemId = lblItemId.getText();

            try {
                boolean isDeleted = ingredientModel.deleteIngredient(itemId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully", ButtonType.OK).show();

                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed to Delete", ButtonType.OK).show();

                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to Delete", ButtonType.OK).show();
            }
        }
    }
    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = ingredientModel.getNextingredientId();
        lblItemId.setText(nextId);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        resetPage();
    }
    public void btnbackPageOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try{
            ancIngredient.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancIngredient.widthProperty());
            anchorPane.prefHeightProperty().bind(ancIngredient.heightProperty());

            ancIngredient.getChildren().setAll(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }


    public void onClickedTable(MouseEvent mouseEvent) {
        IngredientTM selectedItem = tblIngredient.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblItemId.setText(selectedItem.getItemId());
            txtProductId.setText(selectedItem.getProductId());
            txtBatchno.setText(selectedItem.getBatchno());
            txtExpireDate.setText(selectedItem.getDate());
            txtQuantity.setText(String.valueOf(selectedItem.getQty()));
            txtIngredientName.setText(selectedItem.getIngredientName());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }

    }




}

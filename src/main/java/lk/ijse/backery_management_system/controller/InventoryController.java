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
import lk.ijse.backery_management_system.bo.BOFactory;
import lk.ijse.backery_management_system.bo.custom.IngredientBO;
import lk.ijse.backery_management_system.bo.custom.InventoryBO;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.viewTm.InventoryTM;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    public AnchorPane ancInventory;

    public Text lblInventoryId;
    public TextField txtProductId;
    public TextField txtSupplierId;
    public TextField txtRawMaterial;
    public TextField txtQuantity;

    public TableView<InventoryTM> tblInventory;
    public TableColumn<InventoryTM ,String> colInventoryId;
    public TableColumn<InventoryTM ,String> colProductId;
    public TableColumn<InventoryTM ,String> colSupplierId;
    public TableColumn<InventoryTM ,String> colItemName;
    public TableColumn<InventoryTM ,String> colPrice;
    public TableColumn<InventoryTM ,String> colQty;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INVENTORY);

    public Button txtBack;
    public TextField txtSearch;
    public TextField txtItemName;

    public TextField txtIPrice;
    public TextField txtIQty;
    public TextField txtSearch1;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colInventoryId.setCellValueFactory(new PropertyValueFactory<>("inventoryId"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        try {
            resetPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    public void loadTable() throws SQLException, ClassNotFoundException {
        tblInventory.setItems(FXCollections.observableArrayList(
                inventoryBO.getAll().stream()
                        .map(inventoryDto -> new InventoryTM(
                                inventoryDto.getInventoryId(),
                                inventoryDto.getProductId(),
                                inventoryDto.getSupplierId(),
                                inventoryDto.getItemName(),
                                inventoryDto.getPrice(),
                                inventoryDto.getQuantity()
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

            txtProductId.setText(null);
            txtSupplierId.setText(null);
            txtItemName.setText(null);
            txtIPrice.setText(null);
            txtIQty.setText(null);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = inventoryBO.getNextId();
        lblInventoryId.setText(nextId);
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        String supplierId = txtSupplierId.getText();
        String name = txtItemName.getText();
        String price = txtIPrice.getText();
        String quantity = txtIQty.getText();


        int preseprice = Integer.parseInt(price);
        int preseQuantity = Integer.parseInt(quantity);

        ;

        try {
            boolean isSaved = inventoryBO.save( new InventoryDto(
                    inventoryId,
                    productId,
                    supplierId,
                    name,
                    preseprice,
                    preseQuantity


            ));
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
        }
    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String inventoryId = lblInventoryId.getText();
        String productId = txtProductId.getText();
        String supplierId = txtSupplierId.getText();
        String name = txtItemName.getText();
        String price = txtIPrice.getText();
        String quantity = txtIQty.getText();

        int presePrice = Integer.parseInt(price);
        int preseQuantity = Integer.parseInt(quantity);

        InventoryDto inventoryDto = new InventoryDto(
                inventoryId,
                productId,
                supplierId,
                name,
                presePrice,
                preseQuantity
        );

        try {
            boolean isUpdate = inventoryBO.update(inventoryDto);
            if (isUpdate) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Inventory Saved").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong").show();
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
            String inventoryId = lblInventoryId.getText();

            try {
                boolean isDeleted = inventoryBO.delete(inventoryId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Deleted successfully", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Deleting failed", ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "something error", ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        InventoryTM selectedInventory = tblInventory.getSelectionModel().getSelectedItem();

        if (selectedInventory != null) {
            lblInventoryId.setText(selectedInventory.getInventoryId());
            txtProductId.setText(selectedInventory.getProductId());
            txtSupplierId.setText(selectedInventory.getSupplierId());
            txtItemName.setText(selectedInventory.getItemName());
            txtIPrice.setText(String.valueOf(selectedInventory.getPrice()));
            txtIQty.setText(String.valueOf(selectedInventory.getQuantity()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);

        }
    }


    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try{
            ancInventory.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancInventory.widthProperty());
            anchorPane.prefHeightProperty().bind(ancInventory.heightProperty());

            ancInventory.getChildren().setAll(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }
    public void search(KeyEvent keyEvent) {
        String searchText = txtSearch.getText();
        if (searchText.isEmpty()) {
            try {
                loadTable();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load inventory data.").show();
            }
        } else {
            try {
                tblInventory.setItems(FXCollections.observableArrayList(
                        inventoryBO.search(searchText)
                                .stream()
                                .map(inventoryDto -> new InventoryTM(
                                        inventoryDto.getInventoryId(),
                                        inventoryDto.getProductId(),
                                        inventoryDto.getSupplierId(),
                                        inventoryDto.getItemName(),
                                        inventoryDto.getPrice(),
                                        inventoryDto.getQuantity()
                                )).toList()
                ));
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to search items.").show();
            }
        }
    }
    }



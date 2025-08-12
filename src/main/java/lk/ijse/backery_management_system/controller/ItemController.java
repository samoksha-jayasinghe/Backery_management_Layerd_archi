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
import lk.ijse.backery_management_system.bo.custom.InvoiceBO;
import lk.ijse.backery_management_system.bo.custom.ItemBO;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.viewTm.ItemTM;


import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    public AnchorPane ancItem;

    public Text lblItemId;
    public TextField txtname;
    public TextField txtCategory;
    public TextField txtPrice;
    public TextField txtQuantity;
    public TextField txtExpireDate;

    public TableView<ItemTM> tblItem;
    public TableColumn<ItemTM , String> colItemId;
    public TableColumn<ItemTM , String> colName;
    public TableColumn<ItemTM , String> colCategory;
    public TableColumn<ItemTM , Integer> colPrice;
    public TableColumn<ItemTM , Integer> colQuantity;
    public TableColumn<ItemTM , String> colExpireDate;

    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);

    public Button txtBack;
    public TextField txtSearch;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("expirDate"));

        try {
            resetPage();
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }
   /* public void loadTable() throws SQLException, ClassNotFoundException {
        tblItem.setItems(FXCollections.observableArrayList(
                itemModel.getAllItem().stream()
                        .map(itemDto -> new ItemTM(
                               itemDto.getItemId(),
                                itemDto.getName(),
                                itemDto.getCategory(),
                                itemDto.getPrice(),
                                itemDto.getQuantity(),
                                itemDto.getExpirDate()
                        )).toList()
        ));
    }*/

    public void loadTable() throws SQLException, ClassNotFoundException {
        tblItem.setItems(FXCollections.observableArrayList(
                itemBO.getAll().stream()
                        .map(itemDto -> new ItemTM(
                                itemDto.getItemId(),
                                itemDto.getName(),
                                itemDto.getCategory(),
                                itemDto.getPrice(),
                                itemDto.getQuantity(),
                                itemDto.getExpirDate()
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

            txtname.setText(null);
            txtCategory.setText(null);
            txtPrice.setText(null);
            txtQuantity.setText(null);
            txtExpireDate.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = itemBO.getNextId();
        lblItemId.setText(nextId);
    }



    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = lblItemId.getText();
        String name = txtname.getText();
        String category = txtCategory.getText();
        String price = txtPrice.getText();
        String quantity = txtQuantity.getText();
        String expireDate = txtExpireDate.getText();

        int presePrice = Integer.parseInt(price);
        int preseQuantity = Integer.parseInt(quantity);

        ;

        try {
            boolean isSaved = itemBO.save(new ItemDto(
                    id,
                    name,
                    category,
                    presePrice,
                    preseQuantity,
                    expireDate
            ));
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Item Saved", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Error Saving Item", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error Saving Item", ButtonType.OK).show();
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String id = lblItemId.getText();
        String name = txtname.getText();
        String category = txtCategory.getText();
        String price = txtPrice.getText();
        String quantity = txtQuantity.getText();
        String expireDate = txtExpireDate.getText();

        int presePrice = Integer.parseInt(price);
        int preseQuantity = Integer.parseInt(quantity);



        try {
            boolean isUpdate = itemBO.update(new ItemDto(
                    id,
                    name,
                    category,
                    presePrice,
                    preseQuantity,
                    expireDate
            ));
            if (isUpdate) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Item Saved", ButtonType.OK).show();
            }else{
                new Alert(Alert.AlertType.ERROR, "Error Saving Item", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error Saving Item", ButtonType.OK).show();
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
            String itemId = lblItemId.getText();

            try {
                boolean isDeleted = itemBO.delete(itemId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Item Deleted", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Error Deleting Item", ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error Deleting Item", ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void onClickedTable(MouseEvent mouseEvent) {
        ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            lblItemId.setText(selectedItem.getItemId());
            txtname.setText(selectedItem.getName());
            txtCategory.setText(selectedItem.getCategory());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));
            txtExpireDate.setText(selectedItem.getExpirDate());
            txtQuantity.setText(String.valueOf(selectedItem.getQuantity()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try{
            ancItem.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancItem.widthProperty());
            anchorPane.prefHeightProperty().bind(ancItem.heightProperty());

            ancItem.getChildren().setAll(anchorPane);

        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "something error").show();
            e.printStackTrace();

        }
    }


}

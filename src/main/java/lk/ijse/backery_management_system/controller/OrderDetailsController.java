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
import lk.ijse.bakerymanagment.dto.OrderdetailsDto;
import lk.ijse.bakerymanagment.dto.tm.OrderDetailsTM;
import lk.ijse.bakerymanagment.model.OrderdetailsModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {

    public AnchorPane ancOrderDetails;

    public Text lblOrderId;
    public TextField txtProductId;
    public TextField txtQuantity;

    public TableView<OrderDetailsTM> tblOrderDetails;
    public TableColumn<OrderDetailsTM ,String> colOrderId;
    public TableColumn<OrderDetailsTM ,String> colProductId;
    public TableColumn<OrderDetailsTM ,String> colQuantity;



    public Button btnSave;
    public Button btnUpdate;
    public Button btnDelete;
    public Button btnReset;

    private final OrderdetailsModel orderdetailsModel = new OrderdetailsModel();
    //private final

    public Button txtBack;
    public TextField txtSearch;
    public Button btnPlaceOrder;
    public ComboBox<String> cmbProductid;
    public Text lblName;
    public Text lblStockQty;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("qty"));


        try {
            resetPage();
            loadTable();

        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
        try {
            cmbProductid.setItems(FXCollections.observableArrayList());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"").show();
        }

    }
    public void loadTable() throws SQLException, ClassNotFoundException {
        tblOrderDetails.setItems(FXCollections.observableArrayList(
                orderdetailsModel.getAllOrderdetails().stream()
                        .map(orderdetailsDto -> new OrderDetailsTM(
                                orderdetailsDto.getOrderid(),
                                orderdetailsDto.getProductId(),
                                orderdetailsDto.getQty()
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

            cmbProductid.getValue();
            txtQuantity.setText(null);
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }
    }

    private void loadNextId() throws SQLException, ClassNotFoundException {
        String nextId = orderdetailsModel.getNextOrderdetailsId();
        lblOrderId.setText(nextId);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String productId = txtProductId.getText();
        String quantity = txtQuantity.getText();

        int qty = Integer.parseInt(quantity);

        OrderdetailsDto orderdetailsDto = new OrderdetailsDto(
                orderId,
                productId,
                qty
        );

        try {
            boolean isSaved = orderdetailsModel.saveOrderdetails(orderdetailsDto);
            if (isSaved) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Order details save successfully", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
        }

    }


    public void btnUpdateOnAction(ActionEvent actionEvent) {
        String orderId = lblOrderId.getText();
        String productId = txtProductId.getText();
        String quantity = txtQuantity.getText();

        int qty = Integer.parseInt(quantity);

        OrderdetailsDto orderdetailsDto = new OrderdetailsDto(
                orderId,
                productId,
                qty
        );

        try {
            boolean isUpdated = orderdetailsModel.updateOrderdetails(orderdetailsDto);
            if (isUpdated) {
                resetPage();
                new Alert(Alert.AlertType.INFORMATION, "Order details updated successfully", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }catch (Exception e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
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
            String orderId = lblOrderId.getText();

            try {
                boolean isDeleted = orderdetailsModel.deleteOrderdetails(orderId);
                if (isDeleted) {
                    resetPage();
                    new Alert(Alert.AlertType.INFORMATION, "Orderdetails deleted successfully", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
                }
            }catch (Exception e){
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK).show();
            }
        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) {

        resetPage();
    }


    public void onClickedTable(MouseEvent mouseEvent) {
        OrderDetailsTM selectedOrderdetails = tblOrderDetails.getSelectionModel().getSelectedItem();

        if (selectedOrderdetails != null) {
            lblOrderId.setText(selectedOrderdetails.getOrderid());
            cmbProductid.getSelectionModel().getSelectedItem();
            txtQuantity.setText(String.valueOf(selectedOrderdetails.getQty()));

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);

        }
    }

    public void btnBackOnAction(ActionEvent actionEvent) {

        navigateTo("/view/DashBordPage.fxml");
    }

    private void navigateTo(String path) {
        try {
            ancOrderDetails.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOrderDetails.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOrderDetails.heightProperty());

            ancOrderDetails.getChildren().setAll(anchorPane);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
    }


   /* public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        System.out.println("Place order clicked");

        if (tblOrderDetails.getItems().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "There are no order details to place!").show();
            return;
        }

        try {
            System.out.println("Confirmed to place order");
            // Convert TableView items to a list of DTOs
            OrderdetailsDto orderdetailsDto = new OrderdetailsDto();
            for (OrderDetailsTM orderDetailsTM : tblOrderDetails.getItems()) {
                orderdetailsDto = new OrderdetailsDto(
                        orderDetailsTM.getOrderid(),
                        orderDetailsTM.getProductId(),
                        orderDetailsTM.getQty()
                );
            }

            // Save order details via model
            boolean isPlaced = orderdetailsModel.addOrderDetails(orderdetailsDto);
            System.out.println("Order placement returned: " + isPlaced);

            if (isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                resetPage(); // clear all fields and reload table
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to place order! Please try again.").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error occurred while placing order!").show();
        }
    }*/



    public void search(KeyEvent keyEvent) {
        String searchText = txtSearch.getText();
        if (searchText.isEmpty()) {
            try{
                loadTable();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load order items").show();
            }
        }else {
            try {
                ArrayList<OrderdetailsDto> orderdetailsDto = orderdetailsModel.searchOrderItems(searchText);
                tblOrderDetails.setItems(FXCollections.observableArrayList(
                        orderdetailsDto.stream()
                                .map(orderItemDto -> new OrderDetailsTM(
                                        orderItemDto.getOrderid(),
                                        orderItemDto.getProductId(),
                                        orderItemDto.getQty()
                                ))
                                .toList()
                ));
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Failed to load order items").show();
            }
        }
    }
}

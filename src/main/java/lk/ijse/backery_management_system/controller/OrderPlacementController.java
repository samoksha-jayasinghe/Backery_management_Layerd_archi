package lk.ijse.backery_management_system.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.backery_management_system.bo.BOFactory;
import lk.ijse.backery_management_system.bo.custom.*;
import lk.ijse.backery_management_system.db.DBConnection;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.viewTm.OrderCartTM;


import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OrderPlacementController implements Initializable {
    public AnchorPane ancOrderId;

    public Label lblOrderId;
    public Label lblName;
    public Label lblAddCustomer;
    public Label lblAvailableQty;
    public Label lblItemName;

    public ComboBox<String> cmbCustomerId;
    public ComboBox<String> cmbItemId;
    public TextField txtQoh;
    public Label lblItemPrice;

    public TableView<OrderCartTM> tblCart;
    public TableColumn<OrderCartTM , String> colItemId;
    public TableColumn<OrderCartTM , String> colName;
    public TableColumn<OrderCartTM , String> colQuantity;
    public TableColumn<OrderCartTM , String> colPrice;
    public TableColumn<OrderCartTM , String> colTotal;
    public TableColumn<? , ?> colAction;

    private final PLaceOrderBO pLaceOrderBO = (PLaceOrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PLACE_ORDERS);
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    private final OrderBO orderBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDER);
    private final ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);
    private final InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INVENTORY);
    private final OrderdetailsBO orderdetailsBO = (OrderdetailsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDERDETAILS);

    private final ObservableList<OrderCartTM> cartData = FXCollections.observableArrayList();


    public Button btnResetOrder;
    public Button btnPlaceOrder;
    public Button btnAddToCart;
    public Label lblDate;
    public Label lblPrice;
    public Label labelPopUpCustomer;
    public TextField txtCustomerId;
    public Label txtAddToCartQty;
    public TableView tblOrderPlacement;
    public Label lblCustomerName;
    public Label orderPlacementDate;
    public TextField txtCustomerContact;
    public TableColumn colCustomerId;
    public AnchorPane ancOrderPlacementPage;
    public Button btnBack;




    public void goToDashBoardPage(MouseEvent mouseEvent) {
        navigateTo("/view/DashBoardView.fxml");
    }

    private void setCellValues() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("cartQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("remove"));

        tblOrderPlacement.setItems(cartData);
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) throws SQLException {
        String selectedItemId = (String) cmbItemId.getSelectionModel().getSelectedItem();
        String cartQtyString = txtQoh.getText();

        if (selectedItemId == null) {
            new Alert(Alert.AlertType.WARNING, "Please select item..!").show();
            return;
        }

        if (!cartQtyString.matches("^[0-9]+$")) {
            new Alert(Alert.AlertType.WARNING, "Please enter valid quantity..!").show();
            return;
        }

        String itemQtyOnStockString = txtAddToCartQty.getText();
        int cartQty = Integer.parseInt(cartQtyString);
        int itemQtyOnStock = Integer.parseInt(itemQtyOnStockString);

        if (itemQtyOnStock < cartQty) {
            new Alert(Alert.AlertType.WARNING, "Not enough item quantity..!").show();
            return;
        }

        String selectedCustomerId = customerBO.getCustomerNameById(txtCustomerContact.getText());
        String itemName = lblItemName.getText();
        String itemUnitPriceString = lblItemPrice.getText();

        double itemUnitPrice = Double.parseDouble(itemUnitPriceString);
        double total = itemUnitPrice * cartQty;
        //String paymentMethod = (String) cmbPaymentMethod.getSelectionModel().getSelectedItem();

        /*if (paymentMethod == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a payment method..!").show();
            return;
        }*/

        for (OrderCartTM cartTM : cartData) {
            if (cartTM.getItemId().equals(selectedItemId)) {
                int newQty = cartTM.getCartQty() + cartQty;
                if (itemQtyOnStock < newQty) {
                    new Alert(Alert.AlertType.WARNING, "Not enough item quantity..!").show();
                    return;
                }
                cartTM.setCartQty(newQty);
                cartTM.setTotal(newQty * itemUnitPrice);
                tblOrderPlacement.refresh();
                // Update available quantity in UI only
                txtAddToCartQty.setText(String.valueOf(itemQtyOnStock - cartQty));
                return;
            }
        }

        Button removeBtn = new Button("Remove");
        OrderCartTM cartTM = new OrderCartTM(
                selectedItemId,
                selectedCustomerId,
                itemName,
                cartQty,
                itemUnitPrice,
                total,
                removeBtn
        );

        removeBtn.setOnAction(action -> {
            cartData.remove(cartTM);
            tblOrderPlacement.refresh();
        });

        cartData.add(cartTM);
        // Update available quantity in UI only
        txtAddToCartQty.setText(String.valueOf(itemQtyOnStock - cartQty));
        // Do not update inventory in the database here
    }

    public void btnResetOnAction(ActionEvent actionEvent) {
        resetPage();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            // 1. Prepare IDs and data
            //String shipmentId = shipmentModel.getNextShipmentId();
            //String shipmentDate = orderPlacementDate.getText();
            // String trackingNumber = txtShipmentTrackingNumber.getText();

            String orderId = lblOrderId.getText();
            String orderDate = orderPlacementDate.getText();
            String customerContact = txtCustomerContact.getText();
            String customerId = customerBO.getCustomerNameById(customerContact);
            String status = "Shipped";
            String totalAmount = String.valueOf(cartData.stream().mapToDouble(OrderCartTM::getTotal).sum());

            //String paymentId = paymentModel.getNextPaymentId();
            //String paymentMethod = (String) cmbPaymentMethod.getSelectionModel().getSelectedItem();
            int total = (int) cartData.stream().mapToDouble(OrderCartTM::getTotal).sum();

            // Save shipment
            /*boolean shipmentSaved = shipmentModel.saveShipments(
                    new ShipmentDto(
                            shipmentId,
                            trackingNumber,
                            LocalDate.parse(shipmentDate))
            );*/
            /*if (!shipmentSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save shipment!").show();
                return;
            }*/


            // Save order
            boolean orderSaved = orderBO.saveNewOrder(
                    orderId,
                    orderDate,
                    customerId,
                    status,
                    String.valueOf(total)
            );
            if (!orderSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save order!").show();
                return;
            }

            // Save order items and update inventory
            boolean allItemsSaved = true;
            for (OrderCartTM cartTM : cartData) {
                //String orderItemId = itemBO.getNextId();
                boolean itemSaved = itemBO.save(new ItemDto(
                        cartTM.getItemId(),
                        cartTM.getCustomerId(),
                        cartTM.getName(),
                        cartTM.getCartQty(),
                        (int) cartTM.getUnitPrice(),
                        String.valueOf(cartTM.getTotal())


                ));
                boolean inventoryUpdated = inventoryBO.reduceItemQty(
                        cartTM.getItemId(),
                        cartTM.getCartQty()
                );
                if (!itemSaved || !inventoryUpdated) {
                    allItemsSaved = false;
                    break;
                }
            }
            if (!allItemsSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save order items or update inventory!").show();
                return;
            }

            // Save payment
           /* boolean paymentSaved = paymentModel.savePayments(
                    new PaymentDto(
                            paymentId,
                            orderId,
                            totalAmount,
                            paymentMethod)
            );*/
            /*if (!paymentSaved) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
                return;
            }*/

            //Send Email Notification
/*            boolean isEmailSent = EmailUtil.sendMailWithAttachment(

            );*/
/*
            if (!isEmailSent) {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR, "Failed to send email notification!").show();
                return;
            }
*/

            // Commit transaction
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();

//            orderId
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_ORDER_ID", orderId);

            String outputFilePath = LocalDate.now().toString() + ".pdf";



            resetPage();

        } catch (Exception e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error placing order!").show();
        } finally {
            try {
                if (connection != null) connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void goToCustomerPopUp(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerPagePopUp.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(anchorPane));
            stage.setTitle("Add New Customer");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load customer pop-up..!").show();
        }

    }

    public void searchCustomerContact(KeyEvent keyEvent) {
        String contact = txtCustomerContact.getText();
        try {
            if (contact.isEmpty()) {
                lblCustomerName.setText("");
                return;
            }
            String customerContact = customerBO.getCustomerIdByContact(contact);
            if (customerContact != null) {
                lblCustomerName.setText(customerContact);

            } else {
                lblCustomerName.setText("No customer found with this contact");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to search customer..!").show();
        }
    }

    private void navigateTo(String path) {
        try {
            ancOrderPlacementPage.getChildren().clear();

            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource(path));

            anchorPane.prefWidthProperty().bind(ancOrderPlacementPage.widthProperty());
            anchorPane.prefHeightProperty().bind(ancOrderPlacementPage.heightProperty());

            ancOrderPlacementPage.getChildren().add(anchorPane);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Page not found..!").show();
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setCellValues();
        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
        //cmbPaymentMethod.setItems(FXCollections.observableArrayList("Online", "Cash on Delivery", "Card Payment"));


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    ItemDto items = itemBO.getItemById(newValue);
                    lblItemName.setText(items.getName());
                    txtAddToCartQty.setText(String.valueOf(items.getQuantity()));
                    lblItemPrice.setText(String.valueOf(items.getPrice()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    private void loadItemDetails() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemDetails = inventoryBO.getAllInventoryIds();
        cmbItemId.setItems(FXCollections.observableArrayList(itemDetails));
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        lblOrderId.setText(orderBO.getNextId());
        orderPlacementDate.setText(LocalDate.now().toString());

        loadCustomerIds();
        loadItemIds();
    }

    private void loadItemIds() {
        try {
            ArrayList<String> itemIdsList = itemIdsList = inventoryBO.getAllInventoryIds();
            ObservableList<String> itemIds = FXCollections.observableArrayList();
            itemIds.addAll(itemIdsList);
            cmbItemId.setItems(itemIds);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }

    }

    private void loadCustomerIds() {
        try {
            ArrayList<String> customerIdsList = customerBO.getAllCustomerIds();
            ObservableList<String> customerIds = FXCollections.observableArrayList();
            customerIds.addAll(customerIdsList);
            cmbItemId.setItems(customerIds);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load data..!").show();
        }
    }

    private void resetPage() {
        //lblOrderId.setText("");

        loadNextId();
        loadCustomerIds();
        loadItemIds();

        //orderPlacementDate.setText("");
        txtCustomerContact.setText("");
        lblCustomerName.setText("");
        cmbItemId.getSelectionModel().clearSelection();
        lblItemName.setText("");
        txtQoh.setText("");
        txtAddToCartQty.setText("");
        //lblItemPrice.setText("");
        cartData.clear();
        tblOrderPlacement.refresh();
    }

    private void loadNextId() {
        try {
            String nextId = orderBO.getNextId();
            lblOrderId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load next order ID..!").show();
        }
    }

    private void resetWhenAddToCart() {
        cmbItemId.getSelectionModel().clearSelection();
        txtQoh.clear();
        lblItemName.setText("");
        txtAddToCartQty.setText("");
        //lblItemPrice.setText("");
        //cmbPaymentMethod.getSelectionModel().clearSelection();
    }


    public void btnBackOnAction(ActionEvent actionEvent) {
        navigateTo("/view/DashBordPage.fxml");
    }
}




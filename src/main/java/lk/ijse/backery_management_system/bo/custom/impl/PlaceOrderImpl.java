package lk.ijse.backery_management_system.bo.custom.impl;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import lk.ijse.backery_management_system.bo.BOFactory;
import lk.ijse.backery_management_system.bo.custom.*;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.viewTm.OrderCartTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderImpl implements PLaceOrderBO {

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    OrderBO ordersBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDER);
    ItemBO orderItemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);
    InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INVENTORY);

    @Override
    public String getNextOrderId() throws Exception {
        return "";
    }

    @Override
    public ArrayList<String> getAllItemIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public InventoryDto getItemsByIds(String itemId) {
        return null;
    }

    @Override
    public ArrayList<String> getCustomerNamesByPartialPhone(String contact) throws Exception {
        return null;
    }

    @Override
    public void placeOrder(ObservableList<OrderCartTM> cartData, Label lblOrderId, Label orderPlacementDate, TextField txtCustomerContact, ComboBox cmbCustomerName, ComboBox cmbItemId, TextField txtShipmentTrackingNumber, ComboBox cmbPaymentMethod) {

    }

    @Override
    public void addToCart(ComboBox cmbItemId, TextField txtCartQty, Label txtAddToCartQty, Label lblItemName, Label lblItemPrice, ComboBox cmbCustomerName, ComboBox cmbPaymentMethod, ObservableList<OrderCartTM> cartData, TableView<OrderCartTM> tblOrderPlacement) {

    }
}
package lk.ijse.backery_management_system.bo.custom;

import javafx.collections.ObservableList;
import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.viewTm.OrderCartTM;

public interface PLaceOrderBO extends SuperBO {

    public void placeOrder(
            String orderId,
            String orderDate,
            String customerId,
            String status,
            int total,
            ObservableList<OrderCartTM> cartData
    ) throws Exception;
}
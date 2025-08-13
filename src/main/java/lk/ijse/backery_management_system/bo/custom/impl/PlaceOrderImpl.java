package lk.ijse.backery_management_system.bo.custom.impl;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import lk.ijse.backery_management_system.bo.BOFactory;
import lk.ijse.backery_management_system.bo.custom.*;
import lk.ijse.backery_management_system.db.DBConnection;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.viewTm.OrderCartTM;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PlaceOrderImpl implements PLaceOrderBO {

    CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    PaymentBO paymentBO = (PaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    OrderBO ordersBO = (OrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDER);
    ItemBO itemBO = (ItemBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ITEM);
    InventoryBO inventoryBO = (InventoryBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.INVENTORY);
    OrderdetailsBO orderdetailsBO = (OrderdetailsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ORDERDETAILS);

    public void placeOrder(
            String orderId,
            String orderDate,
            String customerId,
            String status,
            int total,
            ObservableList<OrderCartTM> cartData
    ) throws Exception{
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);

            // Save order
            boolean orderSaved =ordersBO .saveNewOrder(
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


            // Commit transaction
            connection.commit();
            new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();

//            orderId
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_ORDER_ID", orderId);

            String outputFilePath = LocalDate.now().toString() + ".pdf";





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
}
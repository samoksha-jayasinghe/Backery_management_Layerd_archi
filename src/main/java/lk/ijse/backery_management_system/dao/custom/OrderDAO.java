package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.entity.OrderEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends CrudDAO<OrderEntity> {
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException;
    public boolean saveNewOrder(String orderId , String customerId , String orderDate , String status , String productId);

}

package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.entity.OrderDetailsEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsDAO extends CrudDAO<OrderDetailsEntity> {
    public ArrayList<String> getAllOrderIds() throws ClassNotFoundException, SQLException;
    public boolean saveNewOrderDetails( String orderId , String productId , int cartQty ) throws ClassNotFoundException, SQLException;

}


package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.entity.InventoryEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryDAO extends CrudDAO<InventoryEntity> {
    public ArrayList<String> getAllInventoryIds() throws SQLException, ClassNotFoundException ;

    public String getInventoryNameById(String customerId) throws SQLException ;

}

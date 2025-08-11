package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.entity.CustomerEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<CustomerEntity> {
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
    public String getCustomerIdByContact(String contact) throws SQLException;
    public String getCustomerNameById(String customerId) throws SQLException;
}

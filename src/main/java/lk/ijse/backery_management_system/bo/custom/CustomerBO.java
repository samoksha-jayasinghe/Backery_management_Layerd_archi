package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.CustomerDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(CustomerDto customerDto) throws SQLException, ClassNotFoundException;
    public ArrayList<CustomerDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(CustomerDto customerDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException;
    public ArrayList<CustomerDto> search(String search) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException;
    public String getCustomerNameById(String customerId) throws SQLException;
    public String generateNewId() throws Exception;
    public ArrayList<CustomerDto> loadAll() throws Exception;
    public String getCustomerIdByContact(String contact) throws SQLException;
}

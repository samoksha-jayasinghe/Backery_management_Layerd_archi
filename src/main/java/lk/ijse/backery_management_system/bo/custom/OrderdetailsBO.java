package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.OrderdetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderdetailsBO extends SuperBO {

    public String getNextId() throws SQLException;
    public boolean save(OrderdetailsDto orderdetailsDto) throws SQLException;
    public ArrayList<OrderdetailsDto> getAll() throws SQLException;
    public boolean update(OrderdetailsDto orderdetailsDto) throws SQLException;
    public boolean delete(String orderdetailsId) throws SQLException;
    public ArrayList<OrderdetailsDto> search(String search) throws SQLException;
    public ArrayList<String> getAllOrderdetailsIds() throws SQLException;
    public String getOrderdetailsIdByContact(String contact) throws SQLException;
    public String getOrderdetailsNameById(String orderdetailsId) throws SQLException;
}

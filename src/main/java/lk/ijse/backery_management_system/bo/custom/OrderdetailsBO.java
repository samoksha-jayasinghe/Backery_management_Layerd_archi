package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.OrderdetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderdetailsBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(OrderdetailsDto orderdetailsDto) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderdetailsDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(OrderdetailsDto orderdetailsDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String orderdetailsId) throws SQLException, ClassNotFoundException;
    public ArrayList<OrderdetailsDto> search(String search) throws SQLException, ClassNotFoundException;
    public ArrayList<String> getAllOrderIds() throws SQLException, ClassNotFoundException;
    public boolean saveNewOrderDetails(String orderId , int Qty ,  String productId ) throws SQLException, ClassNotFoundException;
}

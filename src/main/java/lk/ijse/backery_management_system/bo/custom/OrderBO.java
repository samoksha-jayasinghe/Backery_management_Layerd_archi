package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.OrderDto;
import lk.ijse.backery_management_system.dto.OrderdetailsDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {

    public String getNextId() throws SQLException;
    public boolean save(OrderDto ordersDTO) throws SQLException;
    public boolean update(OrderDto ordersDTO) throws SQLException;
    public boolean delete(String order_id) throws SQLException;
    public ArrayList<OrderDto> search(String search) throws SQLException;
    public ArrayList<OrderDto> getAll() throws SQLException;
    public ArrayList<String> getAllCustomerIds() throws SQLException;
    public ArrayList<String> getAllProductIds() throws SQLException;
    public boolean saveNewOrder(String orderId , String customerId , String orderDate , String status , String productId);

}

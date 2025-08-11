package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.OrderDAO;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.entity.InventoryEntity;
import lk.ijse.backery_management_system.entity.OrderEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select order_id from order order by order_id desc  limit 1");
        char tableCharacter = 'O';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        return tableCharacter + "001";
    }


    public boolean save(OrderEntity orderDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into order values (?,?,?,?,?,?)",
                orderDto.getOrderId(),
                orderDto.getCustomerId(),
                orderDto.getOrderDate(),
                orderDto.getStatus(),
                orderDto.getTotalAmount(),
                orderDto.getProductId()
        );
    }

    public ArrayList<OrderEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from order");
        ArrayList<OrderEntity> orders = new ArrayList<>();
        while (resultSet.next()) {
            orders.add(new OrderEntity(resultSet.getString("order_id"), resultSet.getString("customer_id"), resultSet.getString("order_date"), resultSet.getString("status"), resultSet.getInt("total_amount"), resultSet.getString("product_id")));
        }
        return orders;
    }


    public boolean update(OrderEntity orderDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update order set customer_id=? , order_date=? , status =? , total_amount=? , product_id=? ,  where order_id= ?",
                orderDto.getCustomerId(),
                orderDto.getOrderDate(),
                orderDto.getStatus(),
                orderDto.getTotalAmount(),
                orderDto.getProductId(),
                orderDto.getOrderId()
        );

    }

    public boolean delete(String orderId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from order where order_id = ?",
                orderId
        );
    }

    public ArrayList<OrderEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<OrderEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM order WHERE order_id LIKE ? OR customer_id LIKE ? OR order_date LIKE ? OR status LIKE ? OR total_amount LIKE ? OR product_id LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new OrderEntity(resultSet.getString("order_id"), resultSet.getString("customer_id"), resultSet.getString("order_date"), resultSet.getString("status"), resultSet.getInt("total_amount"), resultSet.getString("product_id")));
        }
        return dtos;
    }


    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }

    @Override
    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException {
        try {
            ResultSet resultSet = SQLUtil.executeQuery("select product_id from product ");
            while (resultSet.next()) {
                getAllProductIds().add(resultSet.getString("product_id"));
            }
            return getAllCustomerIds();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean saveNewOrder(String orderId, String customerId, String orderDate, String status, String productId) {
        try {
            return SQLUtil.executeUpdate(
                    "insert into orders(order_id , customer_id , order_date , status , product_id) values (?,?,?,?,?)",
                    orderId,
                    customerId,
                    orderDate,
                    status,
                    productId
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}




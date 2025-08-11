package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.entity.OrderDetailsEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsDAOImpl {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select Order_id from employee order_details by order_id desc limit 1");
        char tableCharacter = 'O';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String latIdNumberString = lastId.substring(1);
            int latIdNumber = Integer.parseInt(latIdNumberString);
            int nextIdNumber = latIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNumber);
            return nextIdString;
        }

        return tableCharacter + "001";
    }




    public boolean save(OrderDetailsEntity order_detailsDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into order_details values(?,?,?)",
                order_detailsDto.getOrderId(),
                order_detailsDto.getProductId(),
                order_detailsDto.getQty()
        );
    }

    public boolean update(OrderDetailsEntity order_detailsDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update order_details set product_id = ? , quantity = ? where order_id = ?",
                order_detailsDto.getProductId(),
                order_detailsDto.getQty(),
                order_detailsDto.getOrderId()
        );
    }

    public boolean delete(String order_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from order_details where order_id = ?",
                order_id
        );
    }

    public ArrayList<OrderDetailsEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsEntity> dtos = new ArrayList<>();
        String sql = "select * from order_details where order_id LIKE  ? OR product_id LIKE ? OR quantity LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new OrderDetailsEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
        }
        return dtos;
    }

    public ArrayList<OrderDetailsEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from order_details");
        ArrayList<OrderDetailsEntity> order_detailsDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            order_detailsDtoArrayList.add(new OrderDetailsEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3)));
        }

        return order_detailsDtoArrayList;
    }

    public ArrayList<String> getAllOrderIds() throws ClassNotFoundException, SQLException {
        ResultSet rst = SQLUtil.executeQuery("SELECT order_id from orders");
        ArrayList<String> orderIds = new ArrayList<>();

        while (rst.next()) {
            orderIds.add(rst.getString("order_id"));
        }
        return orderIds;
    }


    public boolean saveNewOrderDetails(String orderId, String productId, int cartQty) throws ClassNotFoundException, SQLException {
        try {
            return SQLUtil.executeUpdate(
                    "insert into order_details(order_id ,product_id , quantity ) values (?,?,?)",
                    orderId,
                    productId,
                    cartQty
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}








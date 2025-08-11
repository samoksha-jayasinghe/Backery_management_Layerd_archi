package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dao.custom.PaymentDAO;
import lk.ijse.backery_management_system.entity.InventoryEntity;
import lk.ijse.backery_management_system.entity.PaymentEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select payment_id from payment order by payment_id desc limit 1");
        char tableCharacter = 'P';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String latIdNumberString = lastId.substring(1);
            int latIdNumber = Integer.parseInt(latIdNumberString);
            int nextIdNumber = latIdNumber + 1;
            String nextIdString = String.format(tableCharacter +"%03d", nextIdNumber);
            return nextIdString;
        }

        return tableCharacter + "001";
    }







    public boolean save(PaymentEntity paymentDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into payment values(?,?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getOrderId(),
                paymentDto.getMethod(),
                paymentDto.getPaymentDate(),
                paymentDto.getAmount()
        );
    }

    public boolean update(PaymentEntity paymentDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update payment set order_id = ? , method = ? , payment_date = ? , amount=? where payment_id = ?",
                paymentDto.getOrderId(),
                paymentDto.getMethod(),
                paymentDto.getPaymentDate(),
                paymentDto.getAmount(),
                paymentDto.getPaymentId()
        );
    }

    public boolean delete(String payment_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from payment where payment_id = ?",
                payment_id
        );
    }

    public ArrayList<PaymentEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<PaymentEntity> dtos = new ArrayList<>();
        String sql = "select * from payment where payment_id LIKE  ? OR order_id LIKE ? OR method LIKE ? OR payment_date LIKE ? OR amount LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern , pattern, pattern, pattern , pattern);
        while (resultSet.next()) {
            dtos.add(new PaymentEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
        }
        return dtos;
    }

    public ArrayList<PaymentEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from payment");
        ArrayList<PaymentEntity> paymentDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            paymentDtoArrayList.add(new PaymentEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
        }

        return paymentDtoArrayList;
    }




}


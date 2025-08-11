package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.OrderDto;
import lk.ijse.backery_management_system.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(PaymentDto paymentDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean  update(PaymentDto paymentDTO) throws SQLException, ClassNotFoundException;
    public boolean delete(String payment_id) throws SQLException, ClassNotFoundException;
    public ArrayList<PaymentDto> search(String search) throws SQLException, ClassNotFoundException;

}
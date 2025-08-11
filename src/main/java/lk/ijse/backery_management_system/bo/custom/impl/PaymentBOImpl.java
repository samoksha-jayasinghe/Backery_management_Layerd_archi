package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.PaymentBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.InvoiceDAO;
import lk.ijse.backery_management_system.dao.custom.PaymentDAO;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.PaymentDto;
import lk.ijse.backery_management_system.entity.InvoiceEntity;
import lk.ijse.backery_management_system.entity.PaymentEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    private PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }


    public boolean save(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new PaymentEntity(paymentDto.getPaymentId(),paymentDto.getOrderId(),paymentDto.getMethod(),paymentDto.getPaymentDate(),paymentDto.getAmount()));
    }

    public boolean update(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new PaymentEntity(paymentDto.getPaymentId(),paymentDto.getOrderId(),paymentDto.getMethod(),paymentDto.getPaymentDate(),paymentDto.getAmount()));
    }

    public boolean delete(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }

    public ArrayList<PaymentDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<PaymentEntity> payments = paymentDAO.search(search);
        ArrayList<PaymentDto> dtos = new ArrayList<>();
        for (PaymentEntity payment : payments) {
            dtos.add(new PaymentDto(payment.getPaymentId(),payment.getOrderId(),payment.getMethod(),payment.getPaymentDate(),payment.getAmount()));
        }
        return dtos;
    }

    public ArrayList<PaymentDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentEntity> payments = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (PaymentEntity payment : payments) {
            paymentDtos.add(new PaymentDto(payment.getPaymentId(),payment.getOrderId(),payment.getMethod(),payment.getPaymentDate(),payment.getAmount()));
        }

        return paymentDtos;
    }
}

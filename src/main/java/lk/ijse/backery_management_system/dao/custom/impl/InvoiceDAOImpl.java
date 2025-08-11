package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.InvoiceDAO;
import lk.ijse.backery_management_system.entity.*;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceDAOImpl implements InvoiceDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select invoice_id from invoice order by invoice_id desc  limit 1");
        char tableCharacter = 'I';
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




    public boolean save(InvoiceEntity invoiceDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into invoice values (?,?,?,?)",
                invoiceDto.getInvoiceid(),
                invoiceDto.getOrderid(),
                invoiceDto.getDataIssue(),
                invoiceDto.getTotalAmount()
        );
    }

    public ArrayList<InvoiceEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from invoice");
        ArrayList<InvoiceEntity> invoices = new ArrayList<>();
        while (resultSet.next()) {
            invoices.add(new InvoiceEntity(resultSet.getString("invoice_id") , resultSet.getString("order_id") , resultSet.getString("date_issued") , resultSet.getInt("total_amount")));
        }
        return invoices;
    }




    public boolean update(InvoiceEntity invoiceDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update invoice set order_id=? , date_issued=? , total_amount =? ,  where invoice_id= ?",
                invoiceDto.getOrderid(),
                invoiceDto.getDataIssue(),
                invoiceDto.getTotalAmount(),
                invoiceDto.getInvoiceid()
        );

    }

    public boolean delete(String invoiceId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from invoice where invoice_id = ?",
                invoiceId
        );
    }

    public ArrayList<InvoiceEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM invoice WHERE invoice_id LIKE ? OR order_id LIKE ? OR date_issued LIKE ? OR total_amount LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new InvoiceEntity(resultSet.getString("invoice_id"), resultSet.getString("order_id"), resultSet.getString("date_issued"), resultSet.getInt("total_amount")));
        }
        return dtos;
    }



}


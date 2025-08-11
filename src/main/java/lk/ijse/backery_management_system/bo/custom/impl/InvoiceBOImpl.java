package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.InvoiceBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.InventoryDAO;
import lk.ijse.backery_management_system.dao.custom.InvoiceDAO;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.entity.InventoryEntity;
import lk.ijse.backery_management_system.entity.InvoiceEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class InvoiceBOImpl implements InvoiceBO {
    private InvoiceDAO invoiceDAO = (InvoiceDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INVOICE);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return invoiceDAO.getNextId();
    }


    public boolean save(InvoiceDto invoiceDto) throws SQLException, ClassNotFoundException {
        return invoiceDAO.save(new InvoiceEntity(invoiceDto.getInvoiceid(),invoiceDto.getOrderid(),invoiceDto.getDataIssue(),invoiceDto.getTotalAmount()));
    }

    public boolean update(InvoiceDto invoiceDto) throws SQLException, ClassNotFoundException {
        return invoiceDAO.update(new InvoiceEntity(invoiceDto.getInvoiceid(),invoiceDto.getOrderid(),invoiceDto.getDataIssue(),invoiceDto.getTotalAmount()));
    }

    public boolean delete(String invoice_id) throws SQLException, ClassNotFoundException {
        return invoiceDAO.delete(invoice_id);
    }

    public ArrayList<InvoiceDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceEntity> invoices = invoiceDAO.search(search);
        ArrayList<InvoiceDto> dtos = new ArrayList<>();
        for (InvoiceEntity invoice : invoices) {
            dtos.add(new InvoiceDto(invoice.getInvoiceid(),invoice.getOrderid(),invoice.getDataIssue(),invoice.getTotalAmount()));
        }
        return dtos;
    }

    public ArrayList<InvoiceDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<InvoiceEntity> invoices = invoiceDAO.getAll();
        ArrayList<InvoiceDto> invoiceDtos = new ArrayList<>();
        for (InvoiceEntity invoice : invoices) {
            invoiceDtos.add(new InvoiceDto(invoice.getInvoiceid(),invoice.getOrderid(),invoice.getDataIssue(),invoice.getTotalAmount()));
        }

        return invoiceDtos;
    }
}




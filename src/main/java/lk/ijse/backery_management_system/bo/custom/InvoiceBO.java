package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.dto.InvoiceDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InvoiceBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(InvoiceDto invoiceDto) throws SQLException, ClassNotFoundException;
    public ArrayList<InvoiceDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(InvoiceDto invoiceDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String invoiceId) throws SQLException, ClassNotFoundException;
    public ArrayList<InvoiceDto> search(String search) throws SQLException, ClassNotFoundException;
}

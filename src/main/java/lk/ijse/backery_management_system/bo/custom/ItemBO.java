package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.ItemDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException;
    public ArrayList<ItemDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(ItemDto itemDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String invoiceId) throws SQLException, ClassNotFoundException;
    public ArrayList<ItemDto> search(String search) throws SQLException, ClassNotFoundException;
    public ItemDto getItemById(String itemId) throws SQLException, ClassNotFoundException;

}

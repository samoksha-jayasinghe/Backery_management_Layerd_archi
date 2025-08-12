package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.ProductDto;
import lk.ijse.backery_management_system.dto.SupplierDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;
    public ArrayList<SupplierDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String supplier_id) throws SQLException, ClassNotFoundException;
    public ArrayList<SupplierDto> search(String search) throws SQLException, ClassNotFoundException;

}
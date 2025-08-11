package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.PaymentDto;
import lk.ijse.backery_management_system.dto.ProductDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductBO extends SuperBO {

    public String getNextId() throws SQLException;
    public boolean save(ProductDto productDTO) throws SQLException;
    public ArrayList<ProductDto> getAll() throws SQLException;
    public boolean update(ProductDto productDTO) throws SQLException;
    public boolean delete(String product_id) throws SQLException;
    public ArrayList<ProductDto> search(String search) throws SQLException;


}
package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.dto.ProductDto;
import lk.ijse.backery_management_system.entity.ProductEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO extends CrudDAO<ProductEntity> {
    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException;
    public ProductDto getProductByIds(String productId);
    public boolean reduceQty(String product_id , int cartQty) throws SQLException;

}

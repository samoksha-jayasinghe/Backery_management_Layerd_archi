package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.ProductBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.PaymentDAO;
import lk.ijse.backery_management_system.dao.custom.ProductDAO;
import lk.ijse.backery_management_system.dto.PaymentDto;
import lk.ijse.backery_management_system.dto.ProductDto;
import lk.ijse.backery_management_system.entity.PaymentEntity;
import lk.ijse.backery_management_system.entity.ProductEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductBOImpl implements ProductBO {
    private ProductDAO productDAO = (ProductDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PRODUCT);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return productDAO.getNextId();
    }


    public boolean save(ProductDto productDto) throws SQLException, ClassNotFoundException {
        return productDAO.save(new ProductEntity(productDto.getProductId(),productDto.getName(),productDto.getStocklevel(),productDto.getPrice(),productDto.getCategory(),productDto.getQty(),productDto.getInventory_id()));
    }

    public boolean update(ProductDto productDto) throws SQLException, ClassNotFoundException {
        return productDAO.update(new ProductEntity(productDto.getProductId(),productDto.getName(),productDto.getStocklevel(),productDto.getPrice(),productDto.getCategory(),productDto.getQty(),productDto.getInventory_id()));
    }

    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return productDAO.delete(productId);
    }

    public ArrayList<ProductDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<ProductEntity> products = productDAO.search(search);
        ArrayList<ProductDto> dtos = new ArrayList<>();
        for (ProductEntity product : products) {
            dtos.add(new ProductDto(product.getProductId(),product.getName(),product.getStocklevel(),product.getPrice(),product.getCategory(),product.getQty(),product.getInventory_id()));
        }
        return dtos;
    }

    public ArrayList<ProductDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ProductEntity> products = productDAO.getAll();
        ArrayList<ProductDto> productDtos = new ArrayList<>();
        for (ProductEntity product : products) {
            productDtos.add(new ProductDto(product.getProductId(),product.getName(),product.getStocklevel(),product.getPrice(),product.getCategory(),product.getQty(),product.getInventory_id()));
        }

        return productDtos;
    }
}

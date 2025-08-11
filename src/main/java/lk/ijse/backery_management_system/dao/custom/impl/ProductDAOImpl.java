package lk.ijse.backery_management_system.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dao.custom.ProductDAO;
import lk.ijse.backery_management_system.dto.ProductDto;
import lk.ijse.backery_management_system.entity.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAOImpl implements ProductDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select product_id from product order by product_id desc  limit 1");
        char tableCharacter = 'P';
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




    public boolean save(ProductEntity productDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into product values (?,?,?,?,?,?,?)",
                productDto.getProductId(),
                productDto.getName(),
                productDto.getStocklevel(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getQty(),
                productDto.getInventory_id()

        );
    }

    public ArrayList<ProductEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from product");
        ArrayList<ProductEntity> products = new ArrayList<>();
        while (resultSet.next()) {
            products.add(new ProductEntity(resultSet.getString("product_id") , resultSet.getString("name") , resultSet.getInt("stock_level") , resultSet.getInt("price"),resultSet.getString("category"), resultSet.getInt("qty"), resultSet.getString("inventory_id")));
        }
        return products;
    }




    public boolean update(ProductEntity productDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update ingredient set name=? , stock_level=? , price =? , category=? , qty=? ,  where product_id= ?",
                productDto.getName(),
                productDto.getStocklevel(),
                productDto.getPrice(),
                productDto.getCategory(),
                productDto.getQty(),
                productDto.getInventory_id(),
                productDto.getProductId()
        );

    }

    public boolean delete(String productId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from product where product_id = ?",
                productId
        );
    }

    public ArrayList<ProductEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<ProductEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE product_id LIKE ? OR name LIKE ? OR stock_level LIKE ? OR price LIKE ? OR category LIKE ? OR qty LIKE ? OR inventory_id LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new ProductEntity(resultSet.getString("product_id"), resultSet.getString("name"), resultSet.getInt("stock_level"), resultSet.getInt("price"),resultSet.getString("category"), resultSet.getInt("qty"), resultSet.getString("inventory_id")));
        }
        return dtos;
    }



    public ArrayList<String> getAllProductIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select product_id from product");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }

    public ProductDto getProductByIds(String productId){
        try {
            ResultSet resultSet = SQLUtil.executeQuery("select * from product where product_id = ?", productId);
            if (resultSet.next()) {
                return new ProductDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getString(7)
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load products by Product ID...").show();
        }
        return null;
    }


    public boolean reduceQty(String product_id , int cartQty) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.executeQuery(
                    "select qty from product where product_id = ?",
                    product_id
            );
            if (resultSet.next()) {
                int currentQty = resultSet.getInt("quantity");
                if (currentQty >= cartQty) {
                    int newQty = currentQty - cartQty;
                    return SQLUtil.executeUpdate(
                            "update product set qty = ? where product_id=?",
                            newQty,
                            product_id
                    );
                }else {
                    new Alert(Alert.AlertType.ERROR, "Insufficient stock for product id :  " + product_id).show();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error reducing product quantity...").show();
        }
        return false;
    }
}


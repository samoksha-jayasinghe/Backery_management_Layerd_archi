package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.IngredientDAO;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.entity.IngredientEntity;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientDAOImpl implements IngredientDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select item_id from ingredient order by item_id desc  limit 1");
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





    public boolean save(IngredientEntity ingredientDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into ingredient values (?,?,?,?,?,?)",
                ingredientDto.getItemId(),
                ingredientDto.getProductId(),
                ingredientDto.getBatchno(),
                ingredientDto.getDate(),
                ingredientDto.getQty(),
                ingredientDto.getIngredientName()
        );
    }

    public ArrayList<IngredientEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from ingredient");
        ArrayList<IngredientEntity> ingredients = new ArrayList<>();
        while (resultSet.next()) {
            ingredients.add(new IngredientEntity(resultSet.getString("item_id") , resultSet.getString("product_id") , resultSet.getString("batch_no") , resultSet.getString("expiry_date"),resultSet.getInt("quantity"), resultSet.getString("ingredient_name")));
        }
        return ingredients;
    }





    public boolean update(IngredientEntity ingredientDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update ingredient set product_id=? , batch_no=? , expiry_date =? , quantity=? , ingredient_name=? ,  where item_id= ?",
                ingredientDto.getProductId(),
                ingredientDto.getBatchno(),
                ingredientDto.getDate(),
                ingredientDto.getQty(),
                ingredientDto.getIngredientName(),
                ingredientDto.getItemId()
        );

    }

    public boolean delete(String ingredientId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from ingredient where item_id = ?",
                ingredientId
        );
    }

    public ArrayList<IngredientEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<IngredientEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM ingredient WHERE item_id LIKE ? OR product_id LIKE ? OR batch_no LIKE ? OR expiry_date LIKE ? OR quantity LIKE ? OR user_id ingredient_name ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new IngredientEntity(resultSet.getString("item_id"), resultSet.getString("product_id"), resultSet.getString("batch_no"), resultSet.getString("expiry_date"),resultSet.getInt("quantity"), resultSet.getString("ingredient_name")));
        }
        return dtos;
    }



}


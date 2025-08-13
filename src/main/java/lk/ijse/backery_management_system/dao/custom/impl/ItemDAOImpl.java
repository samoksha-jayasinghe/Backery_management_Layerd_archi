package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.ItemDAO;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.entity.*;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select item_id from item order by item_id desc  limit 1");
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




    public boolean save(ItemEntity itemDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into inventory values (?,?,?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getName(),
                itemDto.getCategory(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getExpirDate()
        );
    }

    public ArrayList<ItemEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from item");
        ArrayList<ItemEntity> items = new ArrayList<>();
        while (resultSet.next()) {
            items.add(new ItemEntity(resultSet.getString("item_id") , resultSet.getString("name") , resultSet.getString("category") , resultSet.getInt("price"),resultSet.getInt("quantity_in_stock"), resultSet.getString("expiry_date")));
        }
        return items;
    }



    public boolean update(ItemEntity itemDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update item set name=? , category=? , price =? , quantity_in_stock=? , expiry_date=? ,  where item_id= ?",
                itemDto.getName(),
                itemDto.getCategory(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getExpirDate(),
                itemDto.getItemId()
        );

    }

    public boolean delete(String itemId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from item where item_id = ?",
                itemId
        );
    }

    public ArrayList<ItemEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<ItemEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE item_id LIKE ? OR name LIKE ? OR category LIKE ? OR price LIKE ? OR quantity_in_stock LIKE ? OR  expiry_date LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new ItemEntity(resultSet.getString("item_id"), resultSet.getString("name"), resultSet.getString("category"), resultSet.getInt("price"),resultSet.getInt("quantity_in_stock"), resultSet.getString("expiry_date")));
        }
        return dtos;
    }

    public ItemEntity getItemById(String itemId) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.executeQuery("select * from item where item_id = ?", itemId);
        if (resultSet.next()) {
            return new ItemEntity(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }



}


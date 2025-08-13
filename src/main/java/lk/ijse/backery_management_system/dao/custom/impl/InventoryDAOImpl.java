package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.InventoryDAO;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryDAOImpl implements InventoryDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select inventory_id from inventory order by inventory_id desc  limit 1");
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




    public boolean save(InventoryEntity inventoryDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into inventory values (?,?,?,?,?,?)",
                inventoryDto.getInventoryId(),
                inventoryDto.getProductId(),
                inventoryDto.getSupplierId(),
                inventoryDto.getItemName(),
                inventoryDto.getPrice(),
                inventoryDto.getQuantity()
        );
    }

    public ArrayList<InventoryEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from inventory");
        ArrayList<InventoryEntity> inventorys = new ArrayList<>();
        while (resultSet.next()) {
            inventorys.add(new InventoryEntity(resultSet.getString("inventory_id") , resultSet.getString("product_id") , resultSet.getString("supplier_id") , resultSet.getString("item_name"),resultSet.getInt("item_price"), resultSet.getInt("itemQty")));
        }
        return inventorys;
    }



    public boolean update(InventoryEntity inventoryDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update ingredient set product_id=? , supplier_id=? , item_name =? , item_price=? , itemQty=? ,  where inventory_id= ?",
                inventoryDto.getProductId(),
                inventoryDto.getSupplierId(),
                inventoryDto.getItemName(),
                inventoryDto.getPrice(),
                inventoryDto.getQuantity(),
                inventoryDto.getInventoryId()
        );

    }

    public boolean delete(String inventoryId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from inventory where inventory_id = ?",
                inventoryId
        );
    }

    public ArrayList<InventoryEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<InventoryEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE inventory_id LIKE ? OR product_id LIKE ? OR supplier_id LIKE ? OR item_name LIKE ? OR item_price LIKE ? OR  itemQty LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new InventoryEntity(resultSet.getString("inventory_id"), resultSet.getString("product_id"), resultSet.getString("supplier_id"), resultSet.getString("item_name"),resultSet.getInt("item_price"), resultSet.getInt("itemQty")));
        }
        return dtos;
    }

    public ArrayList<String> getAllInventoryIds() throws SQLException, ClassNotFoundException {
//        ResultSet resultSet = SQLUtil.executeQuery("select inventory_id from inventory");
//        ArrayList<String> list = new ArrayList<>();
//        while (resultSet.next()) {
//            String id = resultSet.getString(1);
//            list.add(id);
//        }
//        return list;

        ArrayList<String> itemIds = new ArrayList<>();
        ResultSet resultSet = SQLUtil.executeQuery("select inventory_id from inventory");
        while (resultSet.next()) {
            itemIds.add(resultSet.getString("inventory_id"));
        }
        return itemIds;
    }





    public String getInventoryNameById(String customerId) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.executeQuery("select item_name from inventory where inventory_id = ?", customerId);
            if (resultSet.next()) {
                return resultSet.getString("item_name");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "Customer Not found";
    }


}


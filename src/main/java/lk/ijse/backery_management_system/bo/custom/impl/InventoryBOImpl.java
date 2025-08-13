package lk.ijse.backery_management_system.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.backery_management_system.bo.custom.InventoryBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.IngredientDAO;
import lk.ijse.backery_management_system.dao.custom.InventoryDAO;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dto.IngredientDto;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.entity.IngredientEntity;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {
    private InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INVENTORY);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return inventoryDAO.getNextId();
    }


    public boolean save(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.save(new InventoryEntity(inventoryDto.getInventoryId(), inventoryDto.getProductId(), inventoryDto.getSupplierId(), inventoryDto.getItemName(), inventoryDto.getPrice(), inventoryDto.getQuantity()));
    }

    public boolean update(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.update(new InventoryEntity(inventoryDto.getInventoryId(), inventoryDto.getProductId(), inventoryDto.getSupplierId(), inventoryDto.getItemName(), inventoryDto.getPrice(), inventoryDto.getQuantity()));
    }

    public boolean delete(String inventory_id) throws SQLException, ClassNotFoundException {
        return inventoryDAO.delete(inventory_id);
    }

    public ArrayList<InventoryDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<InventoryEntity> inventorys = inventoryDAO.search(search);
        ArrayList<InventoryDto> dtos = new ArrayList<>();
        for (InventoryEntity inventory : inventorys) {
            dtos.add(new InventoryDto(inventory.getInventoryId(), inventory.getProductId(), inventory.getSupplierId(), inventory.getItemName(), inventory.getPrice(), inventory.getQuantity()));
        }
        return dtos;
    }

    public ArrayList<InventoryDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryEntity> inventorys = inventoryDAO.getAll();
        ArrayList<InventoryDto> inventoryDtos = new ArrayList<>();
        for (InventoryEntity inventory : inventorys) {
            inventoryDtos.add(new InventoryDto(inventory.getInventoryId(), inventory.getProductId(), inventory.getSupplierId(), inventory.getItemName(), inventory.getPrice(), inventory.getQuantity()));
        }

        return inventoryDtos;
    }

    public boolean reduceItemQty(String itemId, int cartQty) {
        try {
            ResultSet resultSet = SQLUtil.executeQuery(
                    "SELECT ItemQty FROM inventory WHERE inventory_id = ?",
                    itemId
            );
            if (resultSet.next()) {
                int currentQty = resultSet.getInt("ItemQty");
                if (currentQty >= cartQty) {
                    int newQty = currentQty - cartQty;
                    return SQLUtil.executeUpdate(
                            "UPDATE inventory SET ItemQty = ? WHERE inventory_id = ?",
                            newQty,
                            itemId);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Insufficient stock for Item ID: " + itemId).show();
                    return false;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error reducing item ItemQty...").show();
        }
        return false;
    }

    public ArrayList<String> getAllInventoryIds() throws SQLException, ClassNotFoundException {
        return inventoryDAO.getAllInventoryIds();
    }


    public String getInventoryNameById(String inventoryId) throws SQLException {
        return inventoryDAO.getInventoryNameById(inventoryId);
    }
}



package lk.ijse.backery_management_system.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.backery_management_system.bo.custom.ItemBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.InvoiceDAO;
import lk.ijse.backery_management_system.dao.custom.ItemDAO;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.entity.InvoiceEntity;
import lk.ijse.backery_management_system.entity.ItemEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextId();
    }


    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new ItemEntity(
                itemDto.getItemId(),
                itemDto.getName(),
                itemDto.getCategory(),
                itemDto.getPrice(),
                itemDto.getQuantity(),
                itemDto.getExpirDate()
        ));
    }

    public boolean update(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new ItemEntity(itemDto.getItemId(),itemDto.getName(),itemDto.getCategory(),itemDto.getPrice(),itemDto.getQuantity(),itemDto.getExpirDate()));
    }

    public boolean delete(String item_id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(item_id);
    }

    public ArrayList<ItemDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<ItemEntity> items = itemDAO.search(search);
        ArrayList<ItemDto> dtos = new ArrayList<>();
        for (ItemEntity item : items) {
            dtos.add(new ItemDto(item.getItemId(),item.getName(),item.getCategory(),item.getPrice(),item.getQuantity(),item.getExpirDate()));
        }
        return dtos;
    }

    @Override
    public ItemDto getItemById(String itemId) throws SQLException, ClassNotFoundException {
        ItemEntity item = itemDAO.getItemById(itemId);
        if (item != null) {
            return new ItemDto(
                    item.getItemId(),
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getQuantity(),
                    item.getExpirDate()
            );
        }
        return null;
    }

    public ArrayList<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemEntity> items = itemDAO.getAll();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        for (ItemEntity item : items) {
            itemDtos.add(new ItemDto(item.getItemId(),item.getName(),item.getCategory(),item.getPrice(),item.getQuantity(),item.getExpirDate()));
        }

        return itemDtos;
    }
    public boolean reduceItemQtys(String itemId, int cartQty) {
        try {
            ResultSet resultSet = SQLUtil.executeQuery(
                    "SELECT quantity_in_stock FROM item WHERE item_id = ?",
                    itemId
            );
            if (resultSet.next()) {
                int currentQty = resultSet.getInt("quantity_in_stock");
                if (currentQty >= cartQty) {
                    int newQty = currentQty - cartQty;
                    return SQLUtil.executeUpdate(
                            "UPDATE item SET quantity_in_stock = ? WHERE item_id = ?",
                            newQty,
                            itemId);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Insufficient stock for Item ID: " + itemId).show();
                    return false;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error reducing item quantity_in_stock...").show();
        }
        return false;
    }
}





package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.ItemBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.InvoiceDAO;
import lk.ijse.backery_management_system.dao.custom.ItemDAO;
import lk.ijse.backery_management_system.dto.InvoiceDto;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.entity.InvoiceEntity;
import lk.ijse.backery_management_system.entity.ItemEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ITEM);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextId();
    }


    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new ItemEntity(itemDto.getItemId(),itemDto.getName(),itemDto.getCategory(),itemDto.getPrice(),itemDto.getQuantity(),itemDto.getExpirDate()));
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

    public ArrayList<ItemDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<ItemEntity> items = itemDAO.getAll();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        for (ItemEntity item : items) {
            itemDtos.add(new ItemDto(item.getItemId(),item.getName(),item.getCategory(),item.getPrice(),item.getQuantity(),item.getExpirDate()));
        }

        return itemDtos;
    }
}





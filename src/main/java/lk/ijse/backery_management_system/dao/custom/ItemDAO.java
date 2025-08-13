package lk.ijse.backery_management_system.dao.custom;

import lk.ijse.backery_management_system.dao.CrudDAO;
import lk.ijse.backery_management_system.dto.ItemDto;
import lk.ijse.backery_management_system.entity.ItemEntity;

import java.sql.SQLException;

public interface ItemDAO extends CrudDAO<ItemEntity> {
    public ItemEntity getItemById(String itemId) throws SQLException, ClassNotFoundException;
}

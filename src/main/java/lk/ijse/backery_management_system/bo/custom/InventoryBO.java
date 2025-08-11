package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.IngredientDto;
import lk.ijse.backery_management_system.dto.InventoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException;
    public ArrayList<InventoryDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String inventoryId) throws SQLException, ClassNotFoundException;
    public ArrayList<InventoryDto> search(String search) throws SQLException, ClassNotFoundException;

}

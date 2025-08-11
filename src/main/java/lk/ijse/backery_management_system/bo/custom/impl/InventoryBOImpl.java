package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.InventoryBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.IngredientDAO;
import lk.ijse.backery_management_system.dao.custom.InventoryDAO;
import lk.ijse.backery_management_system.dto.IngredientDto;
import lk.ijse.backery_management_system.dto.InventoryDto;
import lk.ijse.backery_management_system.entity.IngredientEntity;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryBOImpl implements InventoryBO {
    private InventoryDAO inventoryDAO = (InventoryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INVENTORY);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return inventoryDAO.getNextId();
    }


    public boolean save(InventoryDto inventoryDto) throws SQLException, ClassNotFoundException {
        return inventoryDAO.save(new InventoryEntity(inventoryDto.getInventoryId(),inventoryDto.getProductId(),inventoryDto.getSupplierId(),inventoryDto.getItemName(),inventoryDto.getPrice(),inventoryDto.getQuantity()));
    }

    public boolean update(IngredientDto ingredientDto) throws SQLException, ClassNotFoundException {
        return ingredientDAO.update(new IngredientEntity(ingredientDto.getItemId(),ingredientDto.getProductId(),ingredientDto.getBatchno(),ingredientDto.getDate(),ingredientDto.getQty(),ingredientDto.getIngredientName()));
    }

    public boolean delete(String ingredient_id) throws SQLException, ClassNotFoundException {
        return ingredientDAO.delete(ingredient_id);
    }

    public ArrayList<IngredientDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<IngredientEntity> ingredients = ingredientDAO.search(search);
        ArrayList<IngredientDto> dtos = new ArrayList<>();
        for (IngredientEntity ingredient : ingredients) {
            dtos.add(new IngredientDto(ingredient.getItemId(),ingredient.getProductId(),ingredient.getBatchno(),ingredient.getDate(),ingredient.getQty(),ingredient.getIngredientName()));
        }
        return dtos;
    }

    public ArrayList<IngredientDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<IngredientEntity> ingredients = ingredientDAO.getAll();
        ArrayList<IngredientDto> ingredientDtos = new ArrayList<>();
        for (IngredientEntity ingredient : ingredients) {
            ingredientDtos.add(new IngredientDto(ingredient.getItemId(),ingredient.getProductId(),ingredient.getBatchno(),ingredient.getDate(),ingredient.getQty(),ingredient.getIngredientName()));
        }

        return ingredientDtos;
    }
}



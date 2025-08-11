package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.IngredientBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.FeedbackDAO;
import lk.ijse.backery_management_system.dao.custom.IngredientDAO;
import lk.ijse.backery_management_system.dto.FeedbackDto;
import lk.ijse.backery_management_system.dto.IngredientDto;
import lk.ijse.backery_management_system.entity.FeedbackEntity;
import lk.ijse.backery_management_system.entity.IngredientEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientBOImpl implements IngredientBO {
    private IngredientDAO ingredientDAO = (IngredientDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.INGREDIENT);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return ingredientDAO.getNextId();
    }


    public boolean save(IngredientDto ingredientDto) throws SQLException, ClassNotFoundException {
        return ingredientDAO.save(new IngredientEntity(ingredientDto.getItemId(),ingredientDto.getProductId(),ingredientDto.getBatchno(),ingredientDto.getDate(),ingredientDto.getQty(),ingredientDto.getIngredientName()));
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


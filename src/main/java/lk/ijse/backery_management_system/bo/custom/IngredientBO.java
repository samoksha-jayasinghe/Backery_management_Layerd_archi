package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.FeedbackDto;
import lk.ijse.backery_management_system.dto.IngredientDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IngredientBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(IngredientDto ingredientDto) throws SQLException, ClassNotFoundException;
    public ArrayList<IngredientDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(IngredientDto ingredientDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String ingredientId) throws SQLException, ClassNotFoundException;
    public ArrayList<IngredientDto> search(String search) throws SQLException, ClassNotFoundException;

}

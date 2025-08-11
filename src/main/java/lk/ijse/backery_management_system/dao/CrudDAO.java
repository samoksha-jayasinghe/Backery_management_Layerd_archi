package lk.ijse.backery_management_system.dao;

import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public String getNextId() throws SQLException, ClassNotFoundException;

    public boolean save(T dto) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    public boolean update(T dto) throws SQLException, ClassNotFoundException;

    public boolean delete(String id) throws SQLException, ClassNotFoundException;

    public ArrayList<T> search(String search) throws SQLException, ClassNotFoundException;


}

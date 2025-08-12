package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.SupplierDto;
import lk.ijse.backery_management_system.dto.UsersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(UsersDto entity) throws SQLException, ClassNotFoundException;
    public  boolean update(UsersDto entity) throws SQLException, ClassNotFoundException;
    public boolean delete(String userId) throws SQLException, ClassNotFoundException;
    public ArrayList<UsersDto> search(String search) throws SQLException, ClassNotFoundException;
    public ArrayList<UsersDto> getAll() throws SQLException, ClassNotFoundException;

}
package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.SupplierDto;
import lk.ijse.backery_management_system.dto.UsersDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public String getNextId() throws SQLException;
    public boolean save(UsersDto entity) throws SQLException;
    public  boolean update(UsersDto entity) throws SQLException;
    public boolean delete(String userId) throws SQLException;
    public ArrayList<UsersDto> search(String search) throws SQLException;
    public ArrayList<UsersDto> getAll() throws SQLException;

}
package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.UserBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.SupplierDAO;
import lk.ijse.backery_management_system.dao.custom.UsersDAO;
import lk.ijse.backery_management_system.dto.SupplierDto;
import lk.ijse.backery_management_system.dto.UsersDto;
import lk.ijse.backery_management_system.entity.SupplierEntity;
import lk.ijse.backery_management_system.entity.UsersEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    private UsersDAO usersDAO = (UsersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USERS);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return usersDAO.getNextId();
    }


    public boolean save(UsersDto usersDto) throws SQLException, ClassNotFoundException {
        return usersDAO.save(new UsersEntity(usersDto.getUserId(),usersDto.getName(),usersDto.getAddress(),usersDto.getEmail(),usersDto.getContact(),usersDto.getPassword()));
    }

    public boolean update(UsersDto usersDto) throws SQLException, ClassNotFoundException {
        return usersDAO.update(new UsersEntity(usersDto.getUserId(),usersDto.getName(),usersDto.getAddress(),usersDto.getEmail(),usersDto.getContact(),usersDto.getPassword()));
    }

    public boolean delete(String userId) throws SQLException, ClassNotFoundException {
        return usersDAO.delete(userId);
    }

    public ArrayList<UsersDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<UsersEntity> users = usersDAO.search(search);
        ArrayList<UsersDto> dtos = new ArrayList<>();
        for (UsersEntity user : users) {
            dtos.add(new UsersDto(user.getUserId(),user.getName(),user.getAddress(),user.getEmail(),user.getContact(),user.getPassword()));
        }
        return dtos;
    }

    public ArrayList<UsersDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<UsersEntity> users = usersDAO.getAll();
        ArrayList<UsersDto> usersDtos = new ArrayList<>();
        for (UsersEntity user : users) {
            usersDtos.add(new UsersDto(user.getUserId(),user.getName(),user.getAddress(),user.getEmail(),user.getContact(),user.getPassword()));
        }

        return usersDtos;
    }
}


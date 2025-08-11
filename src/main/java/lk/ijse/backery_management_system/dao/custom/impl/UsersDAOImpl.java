package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dao.custom.UsersDAO;
import lk.ijse.backery_management_system.entity.UsersEntity;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAOImpl implements UsersDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select User_id from users order by user_id desc  limit 1");
        char tableCharacter = 'U';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNUmber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d", nextIdNUmber); // "C002"
            return nextIdString;
        }
        return tableCharacter + "001";
    }

    @Override


    public boolean save(UsersEntity usersDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into users values (?,?,?,?,?,?)",
                usersDto.getUserId(),
                usersDto.getName(),
                usersDto.getAddress(),
                usersDto.getEmail(),
                usersDto.getContact(),
                usersDto.getPassword()

        );
    }

    public ArrayList<UsersEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from users");
        ArrayList<UsersEntity> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new UsersEntity(resultSet.getString("user_id") , resultSet.getString("name") , resultSet.getString("address") , resultSet.getString("email"),resultSet.getString("contact"), resultSet.getInt("password")));
        }
        return users;
    }



    public boolean update(UsersEntity usersDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update users set name=? , address=? , email=? , contact =?  , password=? ,  where customer_id= ?",
                usersDto.getName(),
                usersDto.getAddress(),
                usersDto.getEmail(),
                usersDto.getContact(),
                usersDto.getPassword(),
                usersDto.getUserId()
        );

    }

    public boolean delete(String usersId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from users where user_id = ?",
                usersId
        );
    }

    public ArrayList<UsersEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<UsersEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE user_id LIKE ? OR name LIKE ?  OR address LIKE ?  OR email LIKE ? OR contact LIKE  ? OR password LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new UsersEntity(resultSet.getString("user_id"), resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("email"),resultSet.getString("contact"), resultSet.getInt("password")));
        }
        return dtos;
    }


}


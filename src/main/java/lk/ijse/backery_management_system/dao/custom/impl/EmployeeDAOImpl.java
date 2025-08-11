package lk.ijse.backery_management_system.dao.custom.impl;


import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dao.custom.EmployeeDAO;
import lk.ijse.backery_management_system.entity.EmployeeEntity;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select employee_id from employee order by employee_id desc limit 1");
        char tableCharacter = 'E';
        if (resultSet.next()) {
            String lastId = resultSet.getString(1);
            String latIdNumberString = lastId.substring(1);
            int latIdNumber = Integer.parseInt(latIdNumberString);
            int nextIdNumber = latIdNumber + 1;
            String nextIdString = String.format(tableCharacter +"%03d", nextIdNumber);
            return nextIdString;
        }

        return tableCharacter + "001";
    }

    @Override



    public boolean save(EmployeeEntity employeeDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into employee values(?,?,?,?,?)",
                employeeDto.getEmployeeId(),
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getSalary(),
                employeeDto.getContact()
        );
    }

    public boolean update(EmployeeEntity employeeDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update employee set name = ? , role = ? , salary = ? , contact=? where employee_id = ?",
                employeeDto.getName(),
                employeeDto.getRole(),
                employeeDto.getSalary(),
                employeeDto.getEmployeeId()
        );
    }

    public boolean delete(String employee_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from employee where employee_id = ?",
                employee_id
        );
    }

    public ArrayList<EmployeeEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeEntity> dtos = new ArrayList<>();
        String sql = "select * from employee where employee_id LIKE  ? OR name LIKE ? OR role LIKE ? OR salary LIKE ? OR contact LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern , pattern, pattern, pattern , pattern);
        while (resultSet.next()) {
            dtos.add(new EmployeeEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
        }
        return dtos;
    }

    public ArrayList<EmployeeEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from employee");
        ArrayList<EmployeeEntity> employeeDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            employeeDtoArrayList.add(new EmployeeEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
        }

        return employeeDtoArrayList;
    }




}

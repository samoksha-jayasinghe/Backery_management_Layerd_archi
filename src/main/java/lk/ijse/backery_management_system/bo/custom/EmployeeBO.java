package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;
    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String employee_id) throws SQLException, ClassNotFoundException;
    public ArrayList<EmployeeDto> search(String search) throws SQLException, ClassNotFoundException;
    public ArrayList<EmployeeDto> getAll() throws SQLException, ClassNotFoundException;
}

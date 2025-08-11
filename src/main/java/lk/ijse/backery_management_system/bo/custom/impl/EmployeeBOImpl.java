package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.EmployeeBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.EmployeeDAO;
import lk.ijse.backery_management_system.dto.EmployeeDto;
import lk.ijse.backery_management_system.entity.EmployeeEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    private EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }


    public boolean save(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new EmployeeEntity(employeeDto.getEmployeeId(),employeeDto.getName(),employeeDto.getRole(),employeeDto.getSalary(),employeeDto.getContact()));
    }

    public boolean update(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new EmployeeEntity(employeeDto.getEmployeeId(),employeeDto.getName(),employeeDto.getRole(),employeeDto.getSalary(),employeeDto.getContact()));
    }

    public boolean delete(String employee_id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(employee_id);
    }

    public ArrayList<EmployeeDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeEntity> employees = employeeDAO.search(search);
        ArrayList<EmployeeDto> dtos = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            dtos.add(new EmployeeDto(employee.getEmployeeId(),employee.getName(),employee.getRole(),employee.getSalary(),employee.getContact()));
        }
        return dtos;
    }

    public ArrayList<EmployeeDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeEntity> employees = employeeDAO.getAll();
        ArrayList<EmployeeDto> employeeDtos = new ArrayList<>();
        for (EmployeeEntity employee : employees) {
            employeeDtos.add(new EmployeeDto(employee.getEmployeeId(),employee.getName(),employee.getRole(),employee.getSalary(),employee.getContact()));
        }

        return employeeDtos;
    }
}

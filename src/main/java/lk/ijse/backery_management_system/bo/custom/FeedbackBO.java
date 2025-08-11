package lk.ijse.backery_management_system.bo.custom;

import lk.ijse.backery_management_system.bo.SuperBO;
import lk.ijse.backery_management_system.dto.EmployeeDto;
import lk.ijse.backery_management_system.dto.FeedbackDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FeedbackBO extends SuperBO {

    public String getNextId() throws SQLException, ClassNotFoundException;
    public boolean save(FeedbackDto feedbackDto) throws SQLException, ClassNotFoundException;
    public ArrayList<FeedbackDto> getAll() throws SQLException, ClassNotFoundException;
    public boolean update(FeedbackDto feedbackDto) throws SQLException, ClassNotFoundException;
    public boolean delete(String feedbackId) throws SQLException, ClassNotFoundException;
    public ArrayList<FeedbackDto> search(String search) throws SQLException, ClassNotFoundException;

}

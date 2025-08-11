package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.FeedbackBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.EmployeeDAO;
import lk.ijse.backery_management_system.dao.custom.FeedbackDAO;
import lk.ijse.backery_management_system.dto.EmployeeDto;
import lk.ijse.backery_management_system.dto.FeedbackDto;
import lk.ijse.backery_management_system.entity.EmployeeEntity;
import lk.ijse.backery_management_system.entity.FeedbackEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class FeedbackBOImpl implements FeedbackBO {
    private FeedbackDAO feedbackDAO = (FeedbackDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.FEEDBACK);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return feedbackDAO.getNextId();
    }


    public boolean save(FeedbackDto feedbackDto) throws SQLException, ClassNotFoundException {
        return feedbackDAO.save(new FeedbackEntity(feedbackDto.getFeedbackId(),feedbackDto.getCustomerId(),feedbackDto.getOrderId(),feedbackDto.getRating(),feedbackDto.getComment()));
    }

    public boolean update(FeedbackDto feedbackDto) throws SQLException, ClassNotFoundException {
        return feedbackDAO.update(new FeedbackEntity(feedbackDto.getFeedbackId(),feedbackDto.getCustomerId(),feedbackDto.getOrderId(),feedbackDto.getRating(),feedbackDto.getComment()));
    }

    public boolean delete(String feedback_id) throws SQLException, ClassNotFoundException {
        return feedbackDAO.delete(feedback_id);
    }

    public ArrayList<FeedbackDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<FeedbackEntity> feedbacks = feedbackDAO.search(search);
        ArrayList<FeedbackDto> dtos = new ArrayList<>();
        for (FeedbackEntity feedback : feedbacks) {
            dtos.add(new FeedbackDto(feedback.getFeedbackId(),feedback.getCustomerId(),feedback.getOrderId(),feedback.getRating(),feedback.getComment()));
        }
        return dtos;
    }

    public ArrayList<FeedbackDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<FeedbackEntity> feedbacks = feedbackDAO.getAll();
        ArrayList<FeedbackDto> feedbackDtos = new ArrayList<>();
        for (FeedbackEntity feedback : feedbacks) {
            feedbackDtos.add(new FeedbackDto(feedback.getFeedbackId(),feedback.getCustomerId(),feedback.getOrderId(),feedback.getRating(),feedback.getComment()));
        }

        return feedbackDtos;
    }
}

package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.SQLUtil;
import lk.ijse.backery_management_system.dao.custom.FeedbackDAO;
import lk.ijse.backery_management_system.entity.FeedbackEntity;
import lk.ijse.backery_management_system.entity.InventoryEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FeedbackDAOImpl implements FeedbackDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select feedback_id from feedback order by feedback_id desc limit 1");
        char tableCharacter = 'F';
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







    public boolean save(FeedbackEntity feedbackDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into feedback values(?,?,?,?,?)",
                feedbackDto.getFeedbackId(),
                feedbackDto.getCustomerId(),
                feedbackDto.getOrderId(),
                feedbackDto.getRating(),
                feedbackDto.getComment()
        );
    }

    public boolean update(FeedbackEntity feedbackDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update feedback set customer_id = ? , order_id = ? , rating = ? , comment=? where feedback_id = ?",
                feedbackDto.getCustomerId(),
                feedbackDto.getOrderId(),
                feedbackDto.getRating(),
                feedbackDto.getComment(),
                feedbackDto.getFeedbackId()
        );
    }

    public boolean delete(String feedback_id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from feedback where feedback_id = ?",
                feedback_id
        );
    }

    public ArrayList<FeedbackEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<FeedbackEntity> dtos = new ArrayList<>();
        String sql = "select * from feedback where feedback_id LIKE  ? OR customer_id LIKE ? OR order_id LIKE ? OR rating LIKE ? OR comment LIKE ?";
        String pattern = "%" + search + "%";
        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern , pattern, pattern, pattern , pattern);
        while (resultSet.next()) {
            dtos.add(new FeedbackEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)));
        }
        return dtos;
    }

    public ArrayList<FeedbackEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from feedback");
        ArrayList<FeedbackEntity> feedbackDtoArrayList = new ArrayList<>();
        while (resultSet.next()) {
            feedbackDtoArrayList.add(new FeedbackEntity(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)));
        }

        return feedbackDtoArrayList;
    }




}


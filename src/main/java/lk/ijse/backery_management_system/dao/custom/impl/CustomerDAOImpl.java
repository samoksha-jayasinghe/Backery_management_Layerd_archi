package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.CustomerDAO;
import lk.ijse.backery_management_system.entity.CustomerEntity;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select customer_id from customer order by customer_id desc  limit 1");
        char tableCharacter = 'C';
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


    public boolean save(CustomerEntity customerDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into customer values (?,?,?,?,?,?)",
                customerDto.getCustomerId(),
                customerDto.getFirstName(),
                customerDto.getContact(),
                customerDto.getEmail(),
                customerDto.getAddress(),
                customerDto.getUserID()
        );
    }

    public ArrayList<CustomerEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from customer");
        ArrayList<CustomerEntity> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new CustomerEntity(resultSet.getString("customer_id") , resultSet.getString("first_name") , resultSet.getString("address") , resultSet.getString("email"),resultSet.getString("contact"), resultSet.getString("user_id")));
        }
        return customers;
    }

    @Override


    public boolean update(CustomerEntity customerDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update customer set first_name=? , address=? , contact =? , email=? , user_id=? ,  where customer_id= ?",
                customerDto.getFirstName(),
                customerDto.getContact(),
                customerDto.getEmail(),
                customerDto.getAddress(),
                customerDto.getUserID(),
                customerDto.getCustomerId()
        );

    }

    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from customer where customer_id = ?",
                customerId
        );
    }

    public ArrayList<CustomerEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_id LIKE ? OR first_name LIKE ? OR contact LIKE ? OR email LIKE ? OR address LIKE ? OR user_id LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new CustomerEntity(resultSet.getString("customer_id"), resultSet.getString("first_name"), resultSet.getString("address"), resultSet.getString("email"),resultSet.getString("contact"), resultSet.getString("user_id")));
        }
        return dtos;
    }


    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select customer_id from customer");
        ArrayList<String> list = new ArrayList<>();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            list.add(id);
        }
        return list;
    }


    public String getCustomerIdByContact(String contact) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.executeQuery("select customer_id from customer where contact = ?", contact);
            if (resultSet.next()) {
                return resultSet.getString("customer_id");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "Customer Not found";
    }


    public String getCustomerNameById(String customerId) throws SQLException {
        try {
            ResultSet resultSet = SQLUtil.executeQuery("select first_name from customer where customer_id = ?", customerId);
            if (resultSet.next()) {
                return resultSet.getString("first_name");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "Customer Not found";
    }

}


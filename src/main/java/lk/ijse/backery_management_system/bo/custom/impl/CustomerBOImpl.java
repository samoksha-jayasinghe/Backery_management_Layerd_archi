package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.CustomerBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.CustomerDAO;
import lk.ijse.backery_management_system.dto.CustomerDto;
import lk.ijse.backery_management_system.entity.CustomerEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    @Override
    public boolean save(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new CustomerEntity(customerDto.getCustomerId(), customerDto.getFirstName(), customerDto.getAddress(), customerDto.getEmail(),customerDto.getContact(),customerDto.getUserID()));
    }

    @Override
    public ArrayList<CustomerDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerEntity> customers = customerDAO.getAll();
        ArrayList<CustomerDto> customerDTOS = new ArrayList<>();
        for(CustomerEntity customer : customers) {
            customerDTOS.add(new CustomerDto(customer.getCustomerId(), customer.getFirstName(), customer.getAddress(), customer.getEmail(),customer.getContact(),customer.getUserID()));
        }
        return customerDTOS;
    }

    @Override
    public boolean update(CustomerDto customerDto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new CustomerEntity(customerDto.getCustomerId(), customerDto.getFirstName(), customerDto.getAddress(), customerDto.getEmail(), customerDto.getContact(), customerDto.getUserID()));

    }

    @Override
    public boolean delete(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(customerId);
    }

    @Override
    public ArrayList<CustomerDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<CustomerEntity> customers = customerDAO.search(search);

        ArrayList<CustomerDto> dtos = new ArrayList<>();
        for (CustomerEntity c : customers) {
            dtos.add(new CustomerDto(c.getCustomerId(), c.getFirstName(), c.getAddress(), c.getEmail(), c.getContact(), c.getUserID()));
        }
        return dtos;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllCustomerIds();
    }


    public String getCustomerIdByContact(String contact) throws SQLException {
        return customerDAO.getCustomerIdByContact(contact);
    }


    public String getCustomerNameById(String customerId) throws SQLException {
        return customerDAO.getCustomerNameById(customerId);
    }

}

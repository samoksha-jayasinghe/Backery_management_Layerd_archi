package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.OrderBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.CustomerDAO;
import lk.ijse.backery_management_system.dao.custom.OrderDAO;
import lk.ijse.backery_management_system.dto.CustomerDto;
import lk.ijse.backery_management_system.dto.OrderDto;
import lk.ijse.backery_management_system.entity.CustomerEntity;
import lk.ijse.backery_management_system.entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return orderDAO.getNextId();
    }

    @Override
    public boolean save(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new OrderEntity(orderDto.getOrderId(), orderDto.getCustomerId(), orderDto.getOrderdate(), orderDto.getStatus(),orderDto.getTotalAmount(),orderDto.getProductId()));
    }

    @Override
    public ArrayList<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderEntity> orders = orderDAO.getAll();
        ArrayList<OrderDto> orderDtos = new ArrayList<>();
        for(OrderEntity order : orders) {
            orderDtos.add(new OrderDto(order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getStatus(),order.getTotalAmount(),order.getProductId()));
        }
        return orderDtos;
    }

    @Override
    public boolean update(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        return orderDAO.update(new OrderEntity(orderDto.getOrderId(), orderDto.getCustomerId(), orderDto.getOrderdate(), orderDto.getStatus(),orderDto.getTotalAmount(),orderDto.getProductId()));

    }

    @Override
    public boolean delete(String orderId) throws SQLException, ClassNotFoundException {
        return orderDAO.delete(orderId);
    }

    @Override
    public ArrayList<OrderDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<OrderEntity> orders = orderDAO.search(search);

        ArrayList<OrderDto> dtos = new ArrayList<>();
        for (OrderEntity order : orders) {
            dtos.add(new OrderDto(order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getStatus(),order.getTotalAmount(),order.getProductId()));
        }
        return dtos;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllCustomerIds();
    }

    @Override
    public ArrayList<String> getAllProductIds() throws SQLException {
        return null;
    }

    @Override
    public boolean saveNewOrder(String orderId, String customerId, String orderDate, String status, String productId) {
        return false;
    }




}


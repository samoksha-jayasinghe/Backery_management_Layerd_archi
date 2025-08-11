package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.OrderdetailsBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.OrderDAO;
import lk.ijse.backery_management_system.dao.custom.OrderDetailsDAO;
import lk.ijse.backery_management_system.dto.OrderDto;
import lk.ijse.backery_management_system.dto.OrderdetailsDto;
import lk.ijse.backery_management_system.entity.OrderDetailsEntity;
import lk.ijse.backery_management_system.entity.OrderEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderdetailsBOImpl implements OrderdetailsBO {
    OrderDetailsDAO orderDetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.getNextId();
    }

    @Override
    public boolean save(OrderdetailsDto orderdetailsDto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.save(new OrderDetailsEntity(orderdetailsDto.getOrderid(), orderdetailsDto.getProductId(),orderdetailsDto.getQty()));
    }

    @Override
    public ArrayList<OrderdetailsDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsEntity> orderDetails = orderDetailsDAO.getAll();
        ArrayList<OrderdetailsDto> orderdetailsDtos = new ArrayList<>();
        for(OrderDetailsEntity orderDetail : orderDetails) {
            orderdetailsDtos.add(new OrderdetailsDto(orderDetail.getOrderId(), orderDetail.getProductId(),orderDetail.getQty()));
        }
        return orderdetailsDtos;
    }

    @Override
    public boolean update(OrderdetailsDto orderdetailsDto) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.update(new OrderDetailsEntity(orderdetailsDto.getOrderid(), orderdetailsDto.getProductId(),orderdetailsDto.getQty()));

    }

    @Override
    public boolean delete(String orderdetailsId) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.delete(orderdetailsId);
    }

    @Override
    public ArrayList<OrderdetailsDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetailsEntity> orderDetails = orderDetailsDAO.search(search);

        ArrayList<OrderdetailsDto> dtos = new ArrayList<>();
        for (OrderDetailsEntity orderDetail : orderDetails) {
            dtos.add(new OrderdetailsDto(orderDetail.getOrderId(), orderDetail.getProductId(),orderDetail.getQty()));
        }
        return dtos;
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException, ClassNotFoundException {
        return orderDAO.getAllCustomerIds();
    }



    @Override
    public boolean saveNewOrderDetails( String orderId , int Qty , String productId ) throws SQLException, ClassNotFoundException {
        return orderDetailsDAO.saveNewOrderDetails(orderId , Qty , productId);
    }




}



package lk.ijse.backery_management_system.bo.custom.impl;

import lk.ijse.backery_management_system.bo.custom.SupplierBO;
import lk.ijse.backery_management_system.dao.DAOFactory;
import lk.ijse.backery_management_system.dao.custom.ProductDAO;
import lk.ijse.backery_management_system.dao.custom.SupplierDAO;
import lk.ijse.backery_management_system.dto.ProductDto;
import lk.ijse.backery_management_system.dto.SupplierDto;
import lk.ijse.backery_management_system.entity.ProductEntity;
import lk.ijse.backery_management_system.entity.SupplierEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {
    private SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    public String getNextId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextId();
    }


    public boolean save(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new SupplierEntity(supplierDto.getSupplierId(),supplierDto.getName(),supplierDto.getContact(),supplierDto.getAddress()));
    }

    public boolean update(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(new SupplierEntity(supplierDto.getSupplierId(),supplierDto.getName(),supplierDto.getContact(),supplierDto.getAddress()));
    }

    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }

    public ArrayList<SupplierDto> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<SupplierEntity> suppliers = supplierDAO.search(search);
        ArrayList<SupplierDto> dtos = new ArrayList<>();
        for (SupplierEntity supplier : suppliers) {
            dtos.add(new SupplierDto(supplier.getSupplierId(),supplier.getName(),supplier.getContact(),supplier.getAddress()));
        }
        return dtos;
    }

    public ArrayList<SupplierDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierEntity> suppliers = supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        for (SupplierEntity supplier : suppliers) {
            supplierDtos.add(new SupplierDto(supplier.getSupplierId(),supplier.getName(),supplier.getContact(),supplier.getAddress()));
        }

        return supplierDtos;
    }
}

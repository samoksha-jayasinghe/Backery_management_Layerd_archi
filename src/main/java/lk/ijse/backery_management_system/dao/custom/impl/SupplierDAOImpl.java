package lk.ijse.backery_management_system.dao.custom.impl;

import lk.ijse.backery_management_system.dao.custom.SupplierDAO;
import lk.ijse.backery_management_system.entity.*;
import lk.ijse.backery_management_system.dao.custom.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select supplier_id from supplier order by supplier_id desc  limit 1");
        char tableCharacter = 'S';
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




    public boolean save(SupplierEntity supplierDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "insert into invoice values (?,?,?,?)",
                supplierDto.getSupplierId(),
                supplierDto.getName(),
                supplierDto.getContact(),
                supplierDto.getAddress()
        );
    }

    public ArrayList<SupplierEntity> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("select * from supplier");
        ArrayList<SupplierEntity> suppliers = new ArrayList<>();
        while (resultSet.next()) {
            suppliers.add(new SupplierEntity(resultSet.getString("supplier_id") , resultSet.getString("name") , resultSet.getString("contact") , resultSet.getString("address")));
        }
        return suppliers;
    }




    public boolean update(SupplierEntity supplierDto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "update supplier set name=? , contact=? , address =? ,  where supplier_id= ?",
                supplierDto.getName(),
                supplierDto.getContact(),
                supplierDto.getAddress(),
                supplierDto.getSupplierId()
        );

    }

    public boolean delete(String supplierId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate(
                "delete from supplier where supplier_id = ?",
                supplierId
        );
    }

    public ArrayList<SupplierEntity> search(String search) throws SQLException, ClassNotFoundException {
        ArrayList<SupplierEntity> dtos = new ArrayList<>();
        String sql = "SELECT * FROM supplier WHERE supplier_id LIKE ? OR name LIKE ? OR contact LIKE ? OR address LIKE ?";
        String pattern = "%" + search + "%";

        ResultSet resultSet = SQLUtil.executeQuery(sql, pattern, pattern, pattern, pattern);
        while (resultSet.next()) {
            dtos.add(new SupplierEntity(resultSet.getString("supplier_id"), resultSet.getString("name"), resultSet.getString("contact"), resultSet.getString("address")));
        }
        return dtos;
    }



}


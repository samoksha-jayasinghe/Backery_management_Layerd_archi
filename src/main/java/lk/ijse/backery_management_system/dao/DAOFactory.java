package lk.ijse.backery_management_system.dao;

import lk.ijse.backery_management_system.dao.custom.impl.*;

import static lk.ijse.backery_management_system.bo.BOFactory.BOTypes.MAINTENANCE;
import static lk.ijse.backery_management_system.bo.BOFactory.BOTypes.MATERIALINVENTORY;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory() {}

    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes{
        CUSTOMER , EMPLOYEE , FEEDBACK , INGREDIENT , INVENTORY , INVOICE , ITEM , ORDERDETAILS , ORDER , PAYMENT , PRODUCT ,SUPPLIER , USERS;
    }

    public SuperDAO getDAO(DAOTypes daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case FEEDBACK:
                return new FeedbackDAOImpl();
            case INGREDIENT:
                return new IngredientDAOImpl();
            case INVENTORY:
                return new InventoryDAOImpl();
            case INVOICE:
                return new InvoiceDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailsDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case USERS:
                return new UsersDAOImpl();

            default:
                return null;
        }

    }
}

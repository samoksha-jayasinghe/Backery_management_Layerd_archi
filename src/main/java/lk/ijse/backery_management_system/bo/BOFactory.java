package lk.ijse.backery_management_system.bo;

import lk.ijse.backery_management_system.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory() {}

    public static BOFactory getInstance() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMER , EMPLOYEE , FEEDBACK , INGREDIENT , INVENTORY , INVOICE , ITEM , ORDERDETAILS , ORDER ,PLACE_ORDERS , PAYMENT , PRODUCT ,SUPPLIER , USERS;
    }

    public SuperBO getBO(BOTypes type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case FEEDBACK:
                return new FeedbackBOImpl();
            case INGREDIENT:
                return new IngredientBOImpl();
            case INVENTORY:
                return new InventoryBOImpl();
            case INVOICE:
                return new InvoiceBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDERDETAILS:
                return new OrderdetailsBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case PLACE_ORDERS:
                return new PlaceOrderImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case USERS:
                return new UserBOImpl();


            default:
                return null;
        }
    }
}


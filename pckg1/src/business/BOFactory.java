package business;

import business.custom.impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }
    public static BOFactory getInstance(){
        return (boFactory==null) ? (boFactory=new BOFactory()) : boFactory;
    }
    public <T extends SuperBO> T getBO(BOTypes boTypes){
        switch (boTypes){
            case CUSTOMER:
                return (T) new CustomerBOImpl();
            case DELIVERY:
                return (T) new DeliveryBOImpl();
            case ITEM:
                return (T) new ItemBOImpl();
            case LOGIN:
                return (T) new LoginBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            case CATEGORY:
                return (T) new CategoryBOImpl();
            default:
                return null;
        }
    }
}

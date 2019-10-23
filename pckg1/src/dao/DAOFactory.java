package dao;

import dao.custom.impl.*;

public class DAOFactory {
    public static DAOFactory daoFactory;

    public DAOFactory() {
    }
    public static DAOFactory getInstance(){
        return (daoFactory==null) ? (daoFactory=new DAOFactory()) : daoFactory;
    }
    public <T extends SuperDAO> T getDAO (DAOTypes daoTypes){
        switch (daoTypes){
            case ADMIN:
                return (T) new AdminDAOImpl();
            case CATEGORY:
                return (T) new CategoryDAOImpl();
            case CUSTOMER:
                return (T) new CustomerDAOImpl();
            case DELIVERY:
                return (T) new DeliveryDAOImpl();
            case ITEM:
                return (T) new ItemDAOImpl();
            case ORDERDETAIL:
                return (T) new OrderDetailDAOImpl();
            case ORDERS:
                return (T) new OrderDAOImpl();
            case QUERY:
                return (T) new QueryDAOImpl();
            default:
                return null;
        }
    }
}

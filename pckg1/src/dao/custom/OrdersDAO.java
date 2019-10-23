package dao.custom;

import dao.CrudDAO;
import entity.Orders;

public interface OrdersDAO extends CrudDAO<Orders,String> {

    String getLastOrderId()throws Exception;
}

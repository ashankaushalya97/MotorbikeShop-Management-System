package dao.custom;

import dao.CrudDAO;
import entity.Delivery;
import entity.DeliveryPK;

import java.util.List;

public interface DeliveryDAO extends CrudDAO<Delivery, DeliveryPK> {

    String getLastDeliveryId()throws Exception;

    List<Delivery> searchDelivery(String text)throws Exception;
}

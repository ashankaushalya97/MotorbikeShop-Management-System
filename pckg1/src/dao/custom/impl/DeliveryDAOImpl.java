package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.DeliveryDAO;
import entity.Delivery;
import entity.DeliveryPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAOImpl implements DeliveryDAO {

    @Override
    public List<Delivery> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM delivery");
        List<Delivery> deliveries = new ArrayList<>();
        while (rst.next()){
            deliveries.add(new Delivery(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }
        return deliveries;
    }

    @Override
    public Delivery find(DeliveryPK deliveryPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM delivery WHERE  deliveryId=? AND orderId=?",deliveryPK.getDeliveryId(),deliveryPK.getOrderId());
        if(rst.next()){
            return new Delivery(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4));
        }
        return null;
    }

    @Override
    public boolean save(Delivery entity) throws Exception {
        return CrudUtil.execute("INSERT INTO delivery VALUES (?,?,?,?)",entity.getDeliveryPK().getDeliveryId(),entity.getDeliveryPK().getOrderId(),entity.getAddress(),entity.getStates());
    }

    @Override
    public boolean update(Delivery entity) throws Exception {
        return CrudUtil.execute("UPDATE delivery SET address=?, states=? WHERE deliveryId=? AND orderId=?",entity.getAddress(),entity.getStates(),entity.getDeliveryPK().getDeliveryId(),entity.getDeliveryPK().getOrderId());
    }

    @Override
    public boolean delete(DeliveryPK deliveryPK) throws Exception {
        return CrudUtil.execute("DELETE FROM delivery WHERE deliveryId=? AND orderId=?",deliveryPK.getDeliveryId(),deliveryPK.getOrderId());
    }

    @Override
    public String getLastDeliveryId() throws Exception {
        ResultSet rst =CrudUtil.execute("SELECT deliveryId FROM delivery ORDER BY deliveryId DESC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public List<Delivery> searchDelivery(String text) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM delivery WHERE deliveryId LIKE ? OR " +
                "orderId LIKE ? OR address LIKE ? OR states LIKE ?",text,text,text,text);
        List<Delivery> search = new ArrayList<>();
        while (rst.next()){
            search.add(new Delivery(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4)));
        }

        return search;
    }
}

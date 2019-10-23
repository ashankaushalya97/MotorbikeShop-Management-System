package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.OrderDetailPK;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public List<OrderDetail> findAll() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT  * FROM orderDetail");
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rst.next()){
            orderDetails.add(new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4)));
        }
        return orderDetails;
    }

    @Override
    public OrderDetail find(OrderDetailPK orderDetailPK) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT  * FROM orderDetail WHERE  orderId=? AND itemId=?",orderDetailPK.getOrderId(),orderDetailPK.getItemId());
        if(rst.next()){
            return  new OrderDetail(rst.getString(1),rst.getString(2),rst.getInt(3),rst.getDouble(4));
        }
        return null;
    }

    @Override
    public boolean save(OrderDetail entity) throws Exception {
        return CrudUtil.execute("INSERT INTO orderDetail VALUES (?,?,?,?)",entity.getOrderDetailPk().getOrderId(),entity.getOrderDetailPk().getItemId(),entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(OrderDetail entity) throws Exception {
        return CrudUtil.execute("UPDATE orderDetail SET qty=? , unitPrice=? WHERE orderId=? AND itemId=?",entity.getQty(),entity.getUnitPrice(),entity.getOrderDetailPk().getOrderId(),entity.getOrderDetailPk().getItemId());
    }

    @Override
    public boolean delete(OrderDetailPK orderDetailPK) throws Exception {
        return CrudUtil.execute("DELETE  FROM  orderDetail WHERE  orderId=? AND itemId=?",orderDetailPK.getOrderId(),orderDetailPK.getItemId());
    }
}

package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.OrdersDAO;
import entity.Orders;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrdersDAO {

    @Override
    public List<Orders> findAll() throws Exception {
        ResultSet rst= CrudUtil.execute("SELECT * FROM orders");
        List<Orders> orders = new ArrayList<>();
        while (rst.next()){
            orders.add(new Orders(rst.getString(1),rst.getDate(2),rst.getString(3)));
        }
        return orders;
    }

    @Override
    public Orders find(String s) throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orders WHERE  orderId=?",s);
        if(rst.next()){
            return new Orders(rst.getString(1),rst.getDate(2),rst.getString(3));
        }
        return null;
    }

    @Override
    public boolean save(Orders entity) throws Exception {
        return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?)",entity.getOrderId(),entity.getDate(),entity.getCustomerId());
    }

    @Override
    public boolean update(Orders entity) throws Exception {
        return CrudUtil.execute("UPDATE orders SET date=?,customerId=? WHERE  orderId=?",entity.getDate(),entity.getCustomerId(),entity.getOrderId());
    }

    @Override
    public boolean delete(String s) throws Exception {
        return CrudUtil.execute("DELETE FROM orders WHERE  orderId =?",s);
    }

    @Override
    public String getLastOrderId() throws Exception {
        ResultSet rst =CrudUtil.execute("SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if(rst.next()){
            return rst.getString(1);
        }
        return null;
    }
}

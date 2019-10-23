package business.custom.impl;

import DB.DBConnection;
import business.custom.OrderBO;
import dao.DAOFactory;
import dao.DAOTypes;
import dao.custom.ItemDAO;
import dao.custom.OrderDetailDAO;
import dao.custom.OrdersDAO;
import dao.custom.QueryDAO;
import dto.OrderDTO;
import dto.OrderDTO2;
import dto.OrderDetailDTO;
import entity.CustomEntity;
import entity.Item;
import entity.OrderDetail;
import entity.Orders;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    OrdersDAO ordersDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDERS);
    OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOTypes.ORDERDETAIL);
    ItemDAO itemDAO =DAOFactory.getInstance().getDAO(DAOTypes.ITEM);
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOTypes.QUERY);

    @Override
    public String getLastOrderId() throws Exception {
        return ordersDAO.getLastOrderId();
    }

    @Override
    public boolean placeOrder(OrderDTO order) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            boolean result = ordersDAO.save(new Orders(order.getOrderId(),order.getDate(),order.getCustomerId()));

            if(!result){
                connection.rollback();
                System.out.println("saveOrder");
                throw new RuntimeException("Something went wrong!");
            }

            for (OrderDetailDTO orderDetails : order.getOrderDetail() ) {

                result = orderDetailDAO.save(new OrderDetail(order.getOrderId(),orderDetails.getItemId(),orderDetails.getQty(),orderDetails.getUnitPrice()));

                if(!result){
                    System.out.println("Order Details");
                    connection.rollback();
                    throw new RuntimeException("Something went wrong!");
                }

                Item item = itemDAO.find(orderDetails.getItemId());
                int qty=item.getQtyOnHand()-orderDetails.getQty();
                item.setQtyOnHand(qty);
                result = itemDAO.update(item);

                if(!result){
                    connection.rollback();
                    System.out.println("Item");
                    throw new RuntimeException("Something went wrong!");
                }
            }
            connection.commit();
            return true;

        }catch (Throwable e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public List<String> getAllOrderIDs() throws Exception {
        List<Orders> allOrders= ordersDAO.findAll();
        List<String> ids = new ArrayList<>();
        for (Orders allOrder : allOrders) {
            ids.add(allOrder.getOrderId());
        }
        return ids;
    }

    @Override
    public List<OrderDTO2> getOrderInfo() throws Exception {
        List<CustomEntity> orders = queryDAO.getOrderInfo();
        List<OrderDTO2> all = new ArrayList<>();
        for (CustomEntity order : orders) {
            all.add(new OrderDTO2(order.getOrderId(),order.getDate(),order.getCustomerId(),order.getName(),order.getTotal()));
        }

        return all;
    }

    @Override
    public List<OrderDTO2> searchOrder(String text) throws Exception {
        List<CustomEntity> orders = queryDAO.searchOrder(text);
        List<OrderDTO2> all = new ArrayList<>();
        for (CustomEntity order : orders) {
            all.add(new OrderDTO2(order.getOrderId(),order.getDate(),order.getCustomerId(),order.getName(),order.getTotal()));
        }
        return all;
    }


}

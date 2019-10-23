package business.custom.impl;


import com.lowagie.toolbox.plugins.Decrypt;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class OrderBOImplTest {

    public static void main(String[] args) {

        new OrderBOImplTest().placeOrder();

    }

    void getLastOrderId() {
    }


    void placeOrder() {
        OrderDetailDTO orderDetails= new OrderDetailDTO("I001",5,1500.00);
        List<OrderDetailDTO> list = new ArrayList<>();
        list.add(orderDetails);
        try {
            boolean result = new OrderBOImpl().placeOrder(new OrderDTO("I001", Date.valueOf("2019-02-19"),"C001"
                    ,list));
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
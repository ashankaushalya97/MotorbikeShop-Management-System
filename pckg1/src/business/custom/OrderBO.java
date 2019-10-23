package business.custom;

import business.SuperBO;
import dto.OrderDTO;
import dto.OrderDTO2;

import java.util.List;

public interface OrderBO extends SuperBO {

    String getLastOrderId()throws Exception;

    boolean placeOrder(OrderDTO order)throws Exception;

    List<String> getAllOrderIDs ()throws Exception;

    List<OrderDTO2> getOrderInfo()throws Exception;

    List<OrderDTO2> searchOrder(String text)throws Exception;

}

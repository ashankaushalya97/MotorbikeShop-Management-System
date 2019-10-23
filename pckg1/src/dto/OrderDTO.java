package dto;

import java.sql.Date;
import java.util.List;

public class OrderDTO {
    private String orderId;
    private Date date;
    private String customerId;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, Date date, String customerId, List<OrderDetailDTO> orderDetail) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.orderDetail = orderDetail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }
}

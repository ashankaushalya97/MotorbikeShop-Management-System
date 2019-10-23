package entity;

import java.sql.Date;

public class CustomEntity implements SuperEntity {
    private String orderId;
    private Date date;
    private String customerId;
    private String name;
    private double total;

    public CustomEntity() {
    }

    public CustomEntity(String orderId, Date date, String customerId, String name, double total) {
        this.orderId = orderId;
        this.date = date;
        this.customerId = customerId;
        this.name = name;
        this.total = total;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

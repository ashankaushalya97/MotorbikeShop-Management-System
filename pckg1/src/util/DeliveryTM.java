package util;

public class DeliveryTM {
    private String deliveryId;
    private String orderId;
    private String address;
    private String states;

    public DeliveryTM() {
    }

    public DeliveryTM(String deliveryId, String orderId, String address, String states) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.address = address;
        this.states = states;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }
}

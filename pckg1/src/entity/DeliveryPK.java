package entity;

public class DeliveryPK {
    private String deliveryId;
    private String orderId;

    public DeliveryPK() {
    }

    public DeliveryPK(String deliveryId, String orderId) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
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
}

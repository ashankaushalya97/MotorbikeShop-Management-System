package entity;

public class Delivery implements SuperEntity{
    private DeliveryPK deliveryPK;
    private String address;
    private String states;

    public Delivery() {
    }

    public Delivery(DeliveryPK deliveryPK, String address, String states) {
        this.deliveryPK = deliveryPK;
        this.address = address;
        this.states = states;
    }
    public Delivery(String deliveryId,String orderId, String address, String states) {
        this.deliveryPK = new DeliveryPK(deliveryId,orderId);
        this.address = address;
        this.states = states;
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

    public DeliveryPK getDeliveryPK() {
        return deliveryPK;
    }

    public void setDeliveryPK(DeliveryPK deliveryPK) {
        this.deliveryPK = deliveryPK;
    }
}

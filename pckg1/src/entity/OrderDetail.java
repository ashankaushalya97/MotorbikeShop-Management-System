package entity;

public class OrderDetail implements SuperEntity{
    private OrderDetailPK orderDetailPk;
    private int qty;
    private Double unitPrice;

    public OrderDetail() {
    }

    public OrderDetail(OrderDetailPK orderDetailPk, int qty, Double unitPrice) {
        this.orderDetailPk = orderDetailPk;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
    public OrderDetail(String orderId,String itemId, int qty, Double unitPrice) {
        this.orderDetailPk = new OrderDetailPK(orderId,itemId);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public OrderDetailPK getOrderDetailPk() {
        return orderDetailPk;
    }

    public void setOrderDetailPk(OrderDetailPK orderDetailPk) {
        this.orderDetailPk = orderDetailPk;
    }
}

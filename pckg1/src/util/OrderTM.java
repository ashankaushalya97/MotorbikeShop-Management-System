package util;

public class OrderTM {
    private String itemCode;
    private String category;
    private String brand;
    private String description;
    private int qty;
    private double total;

    public OrderTM() {
    }

    public OrderTM(String itemCode, String category, String brand, String description, int qty, double total) {
        this.itemCode = itemCode;
        this.category = category;
        this.brand = brand;
        this.description = description;
        this.qty = qty;
        this.total = total;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

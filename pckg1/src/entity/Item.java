package entity;

public class Item implements SuperEntity{
    private String itemId;
    private String categoryId;
    private String brand;
    private String description;
    private int qtyOnHand;
    private Double buyPrice;
    private Double unitPrice;

    public Item() {
    }

    public Item(String itemId, String categoryId, String brand, String description, int qtyOnHand, Double buyPrice, Double unitPrice) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.brand = brand;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.buyPrice = buyPrice;
        this.unitPrice = unitPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}

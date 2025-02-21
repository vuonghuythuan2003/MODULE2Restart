package ra.entity;

import java.util.Date;

public class Product {
    private int id;
    private String name;
    private int stock;
    private double costPrice;
    private double sellingPrice;
    private Date createdAt;
    private int categoryId;

    // Constructor
    public Product(int id, String name, int stock, double costPrice, double sellingPrice, Date createdAt, int categoryId) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.createdAt = createdAt;
        this.categoryId = categoryId;
    }

    public Product() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", costPrice=" + costPrice +
                ", sellingPrice=" + sellingPrice +
                ", createdAt=" + createdAt +
                ", categoryId=" + categoryId +
                '}';
    }
}

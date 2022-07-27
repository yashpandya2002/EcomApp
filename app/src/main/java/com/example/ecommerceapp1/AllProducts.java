package com.example.ecommerceapp1;

public class AllProducts {
    String name;
    int quantity;
    int  price;
    int productID;

    public AllProducts(String name, int quantity, int price, int productID) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }
}

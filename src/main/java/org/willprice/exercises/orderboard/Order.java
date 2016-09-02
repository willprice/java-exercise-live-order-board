package org.willprice.exercises.orderboard;

public abstract class Order {
    protected float price;
    private final String username;
    protected double quantity;

    public Order(String username, double quantity, float price) {
        this.username = username;
        this.quantity = quantity;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }
}

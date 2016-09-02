package org.willprice.exercises.orderboard;

import java.util.ArrayList;
import java.util.List;

public class Summary {
    private List<Order> orders = new ArrayList<>();

    public Summary(List<Order> orders) {
        this.orders = orders;
    }

    public float getPrice() {
        return orders.stream().findAny().get().getPrice();
    }

    public double getQuantity() {
        return orders.stream().mapToDouble(Order::getQuantity).sum();
    }
}

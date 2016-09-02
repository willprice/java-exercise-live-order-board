package org.willprice.exercises.orderboard;

import java.util.*;
import java.util.stream.Collectors;

public class LiveOrderBoard {
    private final OrderSummariser orderSummariser = new OrderSummariser();
    private Set<Order> orders = new HashSet<>();

    public boolean isRegistered(Order order) {
        return orders.contains(order);
    }

    public void register(Order order) {
        orders.add(order);
    }

    public void cancel(Order order) {
        orders.remove(order);
    }

    public List<Summary> summarise(List<Order> orders) {
        return orderSummariser.summarise(orders);
    }
}

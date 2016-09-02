package org.willprice.exercises.orderboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderSummariser {
    public List<Summary> summarise(List<Order> orders) {
        Stream<Order> buyOrders = orders.stream().filter(order -> order instanceof BuyOrder);
        Stream<Order> sellOrders = orders.stream().filter(order -> order instanceof SellOrder);

        List<Summary> summaries = new ArrayList();

        Comparator<Float> ascendingPriceOrderComparator = (price1, price2) -> (int) (price1 - price2);
        Comparator<Float> descendingPriceOrderComparator = (price1, price2) -> (int) (price2 - price1);

        summaries.addAll(getSummaries(sellOrders, ascendingPriceOrderComparator));
        summaries.addAll(getSummaries(buyOrders, descendingPriceOrderComparator));

        return summaries;
    }

    private List<Summary> getSummaries(Stream<Order> orders, Comparator<Float> priceComparator) {
        Map<Float, List<Order>> ordersGroupedByPrice = orders.collect(Collectors.groupingBy(Order::getPrice));
        return ordersGroupedByPrice.keySet().stream()
                .sorted(priceComparator)
                .map(ordersGroupedByPrice::get)
                .map(Summary::new)
                .collect(Collectors.toList());
    }
}
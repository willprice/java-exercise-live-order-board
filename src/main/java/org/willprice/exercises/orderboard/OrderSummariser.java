package org.willprice.exercises.orderboard;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderSummariser {
    public List<Summary> summarise(List<Order> orders) {
        Comparator<Float> descendingPriceOrderComparator = (price1, price2) -> (int) (price2 - price1);
        Comparator<Float> ascendingPriceOrderComparator = (price1, price2) -> (int) (price1 - price2);

        List<Summary> summaries = new ArrayList();
        summaries.addAll(summariseBuyOrders(orders, descendingPriceOrderComparator));
        summaries.addAll(summariseSellOrders(orders, ascendingPriceOrderComparator));
        return summaries;
    }

    private List<Summary> summariseBuyOrders(List<Order> orders, Comparator<Float> descendingPriceOrderComparator) {
        Stream<Order> buyOrders = orders.stream().filter(order -> order instanceof BuyOrder);
        return getSummaries(buyOrders, descendingPriceOrderComparator);
    }

    private List<Summary> summariseSellOrders(List<Order> orders, Comparator<Float> ascendingPriceOrderComparator) {
        Stream<Order> sellOrders = orders.stream().filter(order -> order instanceof SellOrder);
        return getSummaries(sellOrders, ascendingPriceOrderComparator);
    }

    private List<Summary> getSummaries(Stream<Order> orders, Comparator<Float> priceComparator) {
        return sortSummaries(summariseByPrice(orders), priceComparator)
                .collect(Collectors.toList());
    }

    private Stream<Summary> sortSummaries(Stream<Summary> summaries, Comparator<Float> priceComparator) {
        return summaries
                .sorted((summary1, summary2) -> priceComparator.compare(summary1.getPrice(), summary2.getPrice()));
    }

    private Stream<Summary> summariseByPrice(Stream<Order> orders) {
        return orders.collect(Collectors.groupingBy(Order::getPrice)).values().stream()
                .map(Summary::new);
    }
}
package org.willprice.exercises.orderboard;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class LiveOrderBoardTests {
    private static final double DECIMAL_POINT_ACCURACY = 1.0/Math.pow(10, 5);

    private LiveOrderBoard board;

    @Before
    public void setUp() throws Exception {
        board = new LiveOrderBoard();
    }

    @Test
    public void orderIsRegisteredOnBoard() {
        Order order = new SellOrder("EXAMPLE_USER", 1.2, 306);

        board.register(order);

        assertTrue(board.isRegistered(order));
    }

    @Test
    public void cancellingOrderRemovesItFromBoard() {
        Order order = new SellOrder("EXAMPLE_USER", 1.2, 306);

        board.register(order);
        board.cancel(order);

        assertFalse(board.isRegistered(order));
    }

    @Test
    public void summaryContainsOrderPrice() {
        List<Order> orders = Arrays.asList(
                new SellOrder("EXAMPLE_USER", 1.2, 306)
        );

        Summary summary = board.summarise(orders).get(0);

        assertEquals(306, summary.getPrice(), DECIMAL_POINT_ACCURACY);
    }

    @Test
    @Parameters(method="ordersAndSummaryQuantities")
    public void summaryOfTwoOrdersOfTheSameQuantityContainsSumOfTheirQuantities(List<Order> orders, float expectedTotal) {
        Summary summary = board.summarise(orders).get(0);

        assertEquals(expectedTotal, summary.getQuantity(), DECIMAL_POINT_ACCURACY);
    }

    public Object[] ordersAndSummaryQuantities() {
        return new Object[] {
            new Object[] {
                    Arrays.asList(new SellOrder("EXAMPLE_USER", 1.3, 306)),
                    1.3f
            },
            new Object[] {
                    Arrays.asList(new SellOrder("EXAMPLE_USER", 1.5, 306), new SellOrder("EXAMPLE_USER", 2.0, 306)),
                    3.5f
            }
        };
    }

    @Test
    @Parameters(method = "ordersAndSummaryPricesInOrder")
    public void ordersAreSummarisedBasedOnTheirType(List<Order> orders, List<Float> orderedSummaryPrices) {
        List<Summary> summaries = board.summarise(orders);

        for (int i = 0; i < orderedSummaryPrices.size(); i++) {
            assertEquals(orderedSummaryPrices.get(i), summaries.get(i).getPrice(), DECIMAL_POINT_ACCURACY);
        }
    }

    public Object[] ordersAndSummaryPricesInOrder() {
        return new Object[] {
                new Object[] {
                        Arrays.asList(new SellOrder("EXAMPLE_USER", 1.0f, 305f), new SellOrder("EXAMPLE_USER", 1.0f, 300f)),
                        Arrays.asList(300f, 305f)
                },
                new Object[] {
                        Arrays.asList(new BuyOrder("EXAMPLE_USER", 1.0f, 300f), new SellOrder("EXAMPLE_USER", 1.0f, 305f)),
                        Arrays.asList(305f, 300f)
                },
        };
    }
}

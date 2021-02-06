package storekata;

import storekata.models.Order;

import java.text.DecimalFormat;

public class AppLogic {

    private final InputParser inputParser;
    private final PurchaseTotalCalculator purchaseTotalCalculator;
    private static final DecimalFormat twoPlaces = new DecimalFormat("0.00");

    public AppLogic(InputParser inputParser, PurchaseTotalCalculator purchaseTotalCalculator) {
        this.inputParser = inputParser;
        this.purchaseTotalCalculator = purchaseTotalCalculator;
    }

    public String run(String arg) {
        Order order = inputParser.parse(arg);
        double cost = purchaseTotalCalculator.calculatePurchaseTotal(order.getItems());
        return twoPlaces.format(cost);
    }
}

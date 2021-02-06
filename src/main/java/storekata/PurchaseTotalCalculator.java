package storekata;

import storekata.models.Item;
import storekata.models.Order;

public class PurchaseTotalCalculator {
    public double calculatePurchaseTotal(Order order){
        double sum = order.getItems().stream().mapToDouble(Item::getCost).sum();
        return roundToCents(sum);
    }

    private double roundToCents(double sum) {
        return (double) Math.round(sum * 100) / 100;
    }
}

package storekata;

import storekata.models.Item;

import java.util.List;

public class PurchaseTotalCalculator {
    public double calculatePurchaseTotal(List<Item> items){
        double sum = items.stream().mapToDouble(Item::getCost).sum();
        return roundToCents(sum);
    }

    private double roundToCents(double sum) {
        return (double) Math.round(sum * 100) / 100;
    }
}

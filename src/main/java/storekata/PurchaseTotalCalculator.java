package storekata;

import storekata.models.Item;

import java.util.List;

public class PurchaseTotalCalculator {
    public double calculatePurchaseTotal(List<Item> items){
        return items.get(0).getCost() * items.size();
    }
}

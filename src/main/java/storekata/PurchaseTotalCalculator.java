package storekata;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;
import storekata.repositories.DiscountRepository;

import java.util.List;

public class PurchaseTotalCalculator {
    private DiscountRepository discountRepository;

    public PurchaseTotalCalculator(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public double calculatePurchaseTotal(Order order){
        List<Discount> discounts = discountRepository.getDiscounts();
        for (Discount discount : discounts) {
            order = discount.applyDiscount(order);
        }

        List<Item> purchasedItems = order.getItems();
        double sum = purchasedItems.stream().mapToDouble(Item::getCost).sum();
        return roundToCents(sum);
    }

    private double roundToCents(double sum) {
        return (double) Math.round(sum * 100) / 100;
    }
}

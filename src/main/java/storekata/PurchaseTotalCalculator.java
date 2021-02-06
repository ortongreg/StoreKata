package storekata;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;
import storekata.models.exceptions.AppLogicException;
import storekata.repositories.DiscountRepository;

import java.util.List;

public class PurchaseTotalCalculator {
    private DiscountRepository discountRepository;

    public PurchaseTotalCalculator(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public double calculatePurchaseTotal(Order order){
        List<Discount> discounts = getDiscounts();
        List<Item> purchasedItems = applyDiscountToPurchasedItems(order, discounts);

        double sum = purchasedItems.stream().mapToDouble(Item::getCost).sum();
        return roundToCents(sum);
    }

    private List<Item> applyDiscountToPurchasedItems(Order order, List<Discount> discounts) {
        for (Discount discount : discounts) {
            order = discount.applyDiscount(order);
        }
        return order.getItems();
    }

    private List<Discount> getDiscounts() {
        List<Discount> discounts;
        try {
            discounts = discountRepository.getDiscounts();
        }catch (Exception e){
            throw new AppLogicException("unable to retrieve available discounts");
        }
        return discounts;
    }

    private double roundToCents(double sum) {
        return (double) Math.round(sum * 100) / 100;
    }
}

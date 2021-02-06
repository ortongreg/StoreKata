package storekata.models;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Discount {

    private final LocalDate startDateInclusive;
    private final LocalDate endDateInclusive;

    public Discount(LocalDate startDateInclusive, LocalDate endDateInclusive){
        this.startDateInclusive = startDateInclusive;
        this.endDateInclusive = endDateInclusive;
    }

    public abstract Order applyDiscount(Order order);

    protected List<Item> getItemsOfType(String type, List<Item> items) {
        return items.stream().filter(item -> item.getName().equals(type)).collect(Collectors.toList());
    }

    protected boolean isValidPurchaseDate(
            LocalDate purchaseDate, LocalDate discountStartInclusive, LocalDate discountEndExclusive
    ) {
        boolean isPurchasedAfterStart = !purchaseDate.isBefore(discountStartInclusive);
        boolean isPurchasedBeforeEnd = purchaseDate.isBefore(discountEndExclusive);
        return isPurchasedAfterStart && isPurchasedBeforeEnd;
    }

    protected Order discountedOrder(Order order, List<Item> eligibleItems, double discountedRate) {
        List<Item> items = order.getItems();
        for (Item item : eligibleItems) {
            items.remove(item);
            items.add(new Item(item.getName(), item.getCost() * discountedRate));
        }
        return new Order(order.getPurchaseDate(), items);
    }
}

package storekata.repositories;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountRepositoryImpl  implements DiscountRepository{
    @Override
    public List<Discount> getDiscounts() {
        return Arrays.asList(soupAndBreadDeal(), appleDeal());
    }

    private Discount soupAndBreadDeal() {
        return new Discount() {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> items = order.getItems();
                long soupCount = getItemsOfType("soup", items).size();
                List<Item> breadItems = getItemsOfType("bread", items);

                if( soupCount >= 2 && !breadItems.isEmpty())
                {
                    List<Item> eligibleItems = Collections.singletonList(breadItems.get(0));
                    order = discountedOrder(order, eligibleItems, .5);
                }
                return order;
            }
        };
    }

    private Discount appleDeal(){
        LocalDate discountStartDateInclusive = LocalDate.now().plusDays(3);
        return new Discount() {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> appleItems = getItemsOfType("apple", order.getItems());

                if(!appleItems.isEmpty() && isValidPurchaseDate(order.getPurchaseDate(), discountStartDateInclusive)){
                    order = discountedOrder(order, appleItems, .9);
                }
                return order;
            }
        };
    }

    private List<Item> getItemsOfType(String type, List<Item> items) {
        return items.stream().filter(item -> item.getName().equals(type)).collect(Collectors.toList());
    }

    private boolean isValidPurchaseDate(LocalDate purchaseDate, LocalDate dealStartInclusive) {
        return !purchaseDate.isBefore(dealStartInclusive);
    }

    private Order discountedOrder(Order order, List<Item> eligibleItems, double discountedRate) {
        List<Item> items = order.getItems();
        for (Item item : eligibleItems) {
            items.remove(item);
            items.add(new Item(item.getName(), item.getCost() * discountedRate));
        }
        order = new Order(order.getPurchaseDate(), items);
        return order;
    }

}

package storekata.repositories;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.time.LocalDate;
import java.util.Arrays;
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
                    Item firstBread = breadItems.get(0);
                    items.remove(firstBread);
                    items.add(new Item(firstBread.getName(), firstBread.getCost()/2));
                }
                return new Order(order.getPurchaseDate(), items);
            }
        };
    }

    private Discount appleDeal(){
        LocalDate discountStartDateInclusive = LocalDate.now().plusDays(3);
        return new Discount() {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> items = order.getItems();
                List<Item> appleItems = getItemsOfType("apple", items);
                if(!appleItems.isEmpty() && isValidPurchaseDate(order.getPurchaseDate(), discountStartDateInclusive)){
                    for (Item appleItem : appleItems) {
                        items.remove(appleItem);
                        items.add(new Item(appleItem.getName(), appleItem.getCost() * .9));
                    }
                    order = new Order(order.getPurchaseDate(), items);
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
}

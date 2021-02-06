package storekata.repositories;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountRepositoryImpl  implements DiscountRepository{
    @Override
    public List<Discount> getDiscounts() {
        return Collections.singletonList(soupAndBreadDeal());
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

    private List<Item> getItemsOfType(String type, List<Item> items) {
        return items.stream().filter(item -> item.getName().equals(type)).collect(Collectors.toList());
    }
}

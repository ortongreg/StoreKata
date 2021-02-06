package storekata.repositories;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiscountRepositoryImpl  implements DiscountRepository{
    @Override
    public List<Discount> getDiscounts() {
        Discount discount = new Discount() {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> items = order.getItems();
                long soupCount = items.stream().filter(item -> item.getName().equals("soup")).count();
                long breadCount = items.stream().filter(item -> item.getName().equals("bread")).count();
                if( soupCount >= 2 && breadCount > 0)
                {
                    Item bread = items.stream().filter(item -> item.getName() == "bread").findFirst().get();
                    items.remove(bread);
                    items.add(new Item(bread.getName(), bread.getCost()/2));
                }
                return new Order(order.getPurchaseDate(), items);
            }
        };
        return Collections.singletonList(discount);
    }
}

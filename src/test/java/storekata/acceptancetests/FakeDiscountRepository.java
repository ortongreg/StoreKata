package storekata.acceptancetests;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;
import storekata.repositories.DiscountRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FakeDiscountRepository implements DiscountRepository {
    private List<Discount> discounts = new ArrayList<>();

    @Override
    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void withHalfOffDiscount(){
        discounts.add(
            new Discount() {
                @Override
                public Order applyDiscount(Order order) {
                    Item item = order.getItems().get(0);
                    return new Order(order.getPurchaseDate(), Collections.singletonList(
                            new Item(item.getName(), item.getCost() / 2)
                    ));
                }
            }
        );
    }
}

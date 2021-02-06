package storekata.repositories;

import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiscountRepositoryImpl  implements DiscountRepository{
    @Override
    public List<Discount> getDiscounts() {
        return Arrays.asList(soupAndBreadDeal(), appleDeal());
    }

    private Discount soupAndBreadDeal() {
        LocalDate discountStartDateInclusive = LocalDate.now().minusDays(1);
        LocalDate discountEndDateExclusive = discountStartDateInclusive.plusDays(7);
        return new Discount(discountStartDateInclusive, discountEndDateExclusive) {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> items = order.getItems();
                long soupCount = getItemsOfType("soup", items).size();
                List<Item> breadItems = getItemsOfType("bread", items);

                boolean validPurchaseDate = isValidPurchaseDate(
                        order.getPurchaseDate(),
                        discountStartDateInclusive,
                        discountEndDateExclusive
                );

                if( validPurchaseDate && soupCount >= 2 && !breadItems.isEmpty() )
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
        LocalDate discountEndDateExclusive = LocalDate.now().plusYears(100);
        return new Discount(discountStartDateInclusive, discountEndDateExclusive) {
            @Override
            public Order applyDiscount(Order order) {
                List<Item> appleItems = getItemsOfType("apple", order.getItems());

                boolean validPurchaseDate = isValidPurchaseDate(
                        order.getPurchaseDate(),
                        discountStartDateInclusive,
                        discountEndDateExclusive
                );

                if(!appleItems.isEmpty() && validPurchaseDate){
                    order = discountedOrder(order, appleItems, .9);
                }
                return order;
            }
        };
    }
}

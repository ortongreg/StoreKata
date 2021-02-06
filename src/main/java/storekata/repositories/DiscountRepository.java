package storekata.repositories;

import storekata.models.Discount;

import java.util.List;

public interface DiscountRepository {
    List<Discount> getDiscounts();
}

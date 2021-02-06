package storekata.repositories;

import storekata.models.Discount;

import java.util.ArrayList;
import java.util.List;

public class DiscountRepositoryImpl  implements DiscountRepository{
    @Override
    public List<Discount> getDiscounts() {
        return new ArrayList<>();
    }
}

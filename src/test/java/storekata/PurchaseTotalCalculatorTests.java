package storekata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storekata.acceptancetests.FakeDiscountRepository;
import storekata.models.Discount;
import storekata.models.Item;
import storekata.models.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTotalCalculatorTests {
    private PurchaseTotalCalculator calculator;
    private FakeDiscountRepository fakeDiscountRepository;

    @BeforeEach
    public void BeforeEach(){
        fakeDiscountRepository = new FakeDiscountRepository();
        calculator = new PurchaseTotalCalculator(fakeDiscountRepository);
    }

    @Test
    public void GivenOneItem_WhenCalculatePurchaseTotal_ThenReturnsThatItemCost(){
        double cost = calculator.calculatePurchaseTotal( toOrder(new Item("Foo", 1.23) ));
        assertEquals(1.23, cost);
    }

    @Test
    public void GivenMultipleOfSameItem_WhenCalculatePurchaseTotal_ThenReturnsSum(){
        double cost = calculator.calculatePurchaseTotal(
                toOrder(new Item("Foo", 1.23), new Item("Foo", 1.23)
        ));
        assertEquals(2.46, cost);
    }

    @Test
    public void GivenMultipleDifferentItems_WhenCalculatePurchaseTotal_ThenReturnsSum(){
        double cost = calculator.calculatePurchaseTotal(
                toOrder(new Item("Foo", 1.23), new Item("Bar", 4.56)
        ));
        assertEquals(5.79, cost);
    }

    @Test
    public void GivenOrderAndADiscountThatWouldApply_WhenCalculatePurchaseTotal_ThenDiscountIsApplied() {
        fakeDiscountRepository.withHalfOffDiscount();
        double cost = calculator.calculatePurchaseTotal( toOrder( new Item("DiscountSushi", 2)) );
        assertEquals(1, cost);
    }

    private Order toOrder(Item... items){
        return new Order(LocalDate.now(), Arrays.asList(items));
    }
}

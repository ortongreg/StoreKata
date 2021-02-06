package storekata;

import org.junit.jupiter.api.Test;
import storekata.models.Item;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseTotalCalculatorTests {
    PurchaseTotalCalculator calculator = new PurchaseTotalCalculator();

    @Test
    public void GivenOneItem_WhenCalculatePurchaseTotal_ThenReturnsThatItemCost(){
        double cost = calculator.calculatePurchaseTotal(Collections.singletonList(new Item("Foo")));
        assertEquals(1.23, cost);
    }
}

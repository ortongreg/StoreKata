package storekata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import storekata.acceptancetests.FakeDiscountRepository;
import storekata.models.Item;
import storekata.models.Order;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppLogicTests {
    private AppLogic appLogic;
    @Mock
    private InputParser inputParser;
    private PurchaseTotalCalculator purchaseTotalCalculator;

    @BeforeEach
    public void BeforeEach(){
        purchaseTotalCalculator = new PurchaseTotalCalculator(new FakeDiscountRepository());
        appLogic = new AppLogic(inputParser, purchaseTotalCalculator);
    }

    @Test
    public void GivenTotalThatNeedsFormattingPad_WhenTotalCost_ThenTotalIsFormattedToTwoDecimals(){
        String input = "a big pickle, bought today";
        Item pickle = new Item("pickle", 1.50);
        when(inputParser.parse(input)).thenReturn(new Order(null, Collections.singletonList(pickle)));
        String cost = appLogic.run("a big pickle, bought today");
        assertEquals("1.50", cost);
    }
}

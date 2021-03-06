package storekata.acceptancetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import storekata.App;
import storekata.testdoubles.PrintStreamSpy;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppAcceptanceTests {
    private PrintStreamSpy printStreamSpy;
    private PrintStream console;

    @BeforeEach
    public void BeforeEach(){
        console = System.out;
        printStreamSpy = new PrintStreamSpy();
        System.setOut(printStreamSpy);
    }

    @Test
    public void OrderWithMultipleOfAnItemButNoDeal(){
        App.main(new String[]{"2 tins of soup, bought today"});

        assertEquals("1.30", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithMultipleItemsNoDeal(){
        App.main(new String[]{"2 tins of soup and a bottle of milk, bought today"});

        assertEquals("2.60", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithADeal(){
        App.main(new String[]{"2 tins of soup and a loaf of bread, bought today"});

        assertEquals("1.70", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithADealStartingInTheFuture(){
        App.main(new String[]{"6 apples and a bottle of milk, bought in 5 days time"});

        assertEquals("1.84", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithADealThatHasEnded(){
        App.main(new String[]{"2 tins of soup and a loaf of bread, bought in 6 days time"});

        assertEquals("2.10", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithDealThatExpiresOnDynamicDate_JustExpired(){
        LocalDate now = LocalDate.now();
        LocalDate startOfNextNextMonth = now.plusMonths(2).withDayOfMonth(1);
        long daysUntilStartOfNextNextMonth = now.until(startOfNextNextMonth, ChronoUnit.DAYS);
        String input = String.format("a single apple, bought in %s days time", daysUntilStartOfNextNextMonth);
        App.main(new String[]{input});

        assertEquals("0.10", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderWithDealThatExpiresOnDynamicDate_LastDay(){
        LocalDate now = LocalDate.now();
        LocalDate startOfNextNextMonth = now.plusMonths(2).withDayOfMonth(1).minusDays(1);
        long daysUntilStartOfNextNextMonth = now.until(startOfNextNextMonth, ChronoUnit.DAYS);
        String input = String.format("a single apple, bought in %s days time", daysUntilStartOfNextNextMonth);
        App.main(new String[]{input});

        assertEquals("0.09", printStreamSpy.lastPrint);
    }

    @Test
    public void OrderPurchasedInThePast_SupportMinusInput(){
        App.main(new String[]{"3 tins of soup and a loaf of bread, bought in -2 days time"});

        assertEquals("2.75", printStreamSpy.lastPrint);
    }

    @Test
    public void GlobalErrorHandling(){
        App.main(null);

        assertEquals("unable to calculate total", printStreamSpy.lastPrint);
    }

    @Test
    public void AppLogicErrorHandling(){
        App.main(new String[]{"Im a little teapot"});

        assertEquals("unable to parse 'Im a little teapot'", printStreamSpy.lastPrint);
    }

    @Test
    public void OnlyApplyEachDiscountOnce(){
        App.main(new String[]{"4 tins of soup, 2 loaves of bread, 1 single apple and 1 bottle of milk, bought in 3 days time"});

        assertEquals("5.19", printStreamSpy.lastPrint);
    }

    @DisplayName("Check against kata requirements")
    @ParameterizedTest(name = "input:''{0}")
    @CsvSource({
            "'3 tins of soup and 2 loaves of bread, bought today', 3.15",
            "'6 apples and a bottle of milk, bought today', 1.90",
            "'6 apples and a bottle of milk, bought in 5 days time', 1.84",
            "'3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time', 1.97"
    })
    public void CalculateTotalCost(String input, String expectedCost){
        App.main(new String[]{input});

        assertEquals(expectedCost, printStreamSpy.lastPrint);
    }

    @AfterEach
    public void AfterEach(){
        System.setOut(console);
    }
}

package storekata;

import org.junit.jupiter.api.Test;
import storekata.models.Order;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTests {
    InputParser parser = new InputParser();

    @Test
    public void GivenOnlySoupBoughtToday_WhenParse_ThenParsesCorrectDay(){
        String input = "a tin of soup, bought today";
        Order result = parser.parse(input);

        LocalDate today = LocalDate.now();
        assertEquals(today, result.getPurchaseDate());
    }

    @Test
    public void GivenOneSoupBoughtInFiveDaysTime_WhenParse_ThenParsesCorrectDay(){
        String input = "1 tin of soup, bought in 5 days time";
        Order result = parser.parse(input);

        LocalDate fiveDaysFromNow = LocalDate.now().plusDays(5);
        assertEquals(fiveDaysFromNow, result.getPurchaseDate());
    }

    @Test
    public void GivenOneSoupBoughtInTwoDigitDaysTime_WhenParse_ThenParsesCorrectDay(){
        String input = "1 tin of soup, bought in 10 days time";
        Order result = parser.parse(input);

        LocalDate fiveDaysFromNow = LocalDate.now().plusDays(10);
        assertEquals(fiveDaysFromNow, result.getPurchaseDate());
    }

    @Test
    public void GivenOneSoupBoughtToday_WhenParse_ThenParsesOneSoup(){
        String input = "1 tin of soup, bought today";
        Order result = parser.parse(input);

        List<String> expectedItems = Collections.singletonList("soup");
        assertEquals(expectedItems, result.getItems());
    }
}

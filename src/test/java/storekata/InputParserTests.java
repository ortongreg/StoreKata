package storekata;

import org.junit.jupiter.api.Test;
import storekata.models.Item;
import storekata.models.Order;
import storekata.testdoubles.ItemRepositoryStub;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputParserTests {
    InputParser parser = new InputParser(new ItemRepositoryStub());

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

        List<Item> expectedItems = toItems("soup");
        assertEquals(expectedItems, result.getItems());
    }

    @Test
    public void GivenMoreThanOneSoupBoughtToday_WhenParse_ThenParsesMultipleSoup(){
        String input = "3 tins of soup, bought today";
        Order result = parser.parse(input);

        List<Item> expectedItems = toItems("soup", "soup", "soup");
        assertEquals(expectedItems, result.getItems());
    }

    @Test
    public void GivenApplesBoughtToday_WhenParse_ThenParsesApple(){
        String input = "2 Apples, bought today";
        Order result = parser.parse(input);

        List<Item> expectedItems = toItems("apple", "apple");
        assertEquals(expectedItems, result.getItems());
    }

    @Test
    public void GivenApplesAndSoupBoughtToday_WhenParse_ThenParsesApplesAndSoup(){
        String input = "2 apples and 2 tins of soup, bought today";
        Order result = parser.parse(input);

        List<Item> expectedItems = toItems("apple", "apple", "soup", "soup");
        assertEquals(expectedItems, result.getItems());
    }

    @Test
    public void GivenApplesAndSoupAndBreadWithCommaBoughtToday_WhenParse_ThenParsesApplesAndSoup(){
        String input = "2 apples, a loaf of bread and 2 tins of soup, bought today";
        Order result = parser.parse(input);

        List<Item> expectedItems = toItems("apple", "apple", "bread", "soup", "soup");
        assertEquals(expectedItems, result.getItems());
    }

    @Test
    public void GivenMinusDays_WhenParse_ThenParsesDateInThePast(){
        String input = "3 tins of soup and a loaf of bread, bought in -2 days time";
        Order result = parser.parse(input);

        LocalDate twoDaysAgo = LocalDate.now().minusDays(2);
        assertEquals(twoDaysAgo, result.getPurchaseDate());
    }

    private List<Item> toItems(String... itemNames){
        List<Item> items = new ArrayList<>();
        for (String itemName : itemNames) {
            items.add(new Item(itemName, 1.23));
        }
        return items;
    }
}

package storekata;

import storekata.models.Item;
import storekata.models.Order;
import storekata.models.exceptions.ParseException;
import storekata.repositories.ItemRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InputParser {
    private static final String ORDER_PATTERN_STR = "(.*),\\sbought\\s(today|(in\\s-?\\d+\\sdays\\stime))";
    private static final Pattern ORDER_PATTERN = Pattern.compile(ORDER_PATTERN_STR);

    private static final String PURCHASE_DAY_PATTERN_STR = "in\\s(-?\\d+)\\sdays\\stime";
    private static final Pattern PURCHASE_DAY_PATTERN = Pattern.compile(PURCHASE_DAY_PATTERN_STR);

    private static final String ITEM_PATTERN_STR = "(a|\\d+).*";
    private static final Pattern ITEM_PATTERN = Pattern.compile(ITEM_PATTERN_STR);

    private ItemRepository itemRepository;

    public InputParser(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Order parse(String order){
        try{
            Matcher matcher = ORDER_PATTERN.matcher(order);
            matcher.matches();

            List<Item> items = parseItems(matcher.group(1));

            String purchaseDayString = matcher.group(2);
            LocalDate purchaseDate = parsePurchaseDate(purchaseDayString);

            return new Order(purchaseDate, items);
        }catch (Exception e){
            String message = String.format("unable to parse '%s'", order);
            throw new ParseException(message);
        }
    }

    private List<Item> parseItems(String itemsString){
        List<Item> result = new ArrayList<>();

        List<String> purchasedTypes = Arrays.asList(itemsString.split("(and|,)\\s"));
        purchasedTypes.forEach(purchasedType -> {
            result.addAll(parseItem(purchasedType));
        });
        return result;
    }

    private List<Item> parseItem(String itemString){
        List<Item> result = new ArrayList<>();
        Item itemType = itemRepository.allItems().stream()
                .filter(type -> itemString.toLowerCase(Locale.ROOT).contains(type.getName()))
                .collect(Collectors.toList()).get(0);

        Matcher matcher = ITEM_PATTERN.matcher(itemString);
        matcher.matches();

        int itemCount = canParseInt(matcher.group(1)) ?
                Integer.parseInt(matcher.group(1)) : 1;

        for(int i = 0; i< itemCount; i++){
            result.add(itemType);
        }
        return result;
    }

    private LocalDate parsePurchaseDate(String purchaseDayString) {
        Matcher purchaseDayMatcher = PURCHASE_DAY_PATTERN.matcher(purchaseDayString);
        int purchaseDateOffset = 0;
        if(purchaseDayMatcher.matches()){
            String purchaseDateOffsetStr = purchaseDayMatcher.group(1);
            purchaseDateOffset = Integer.parseInt(purchaseDateOffsetStr);
        }
        return LocalDate.now().plusDays(purchaseDateOffset);
    }

    private boolean canParseInt(String value){
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

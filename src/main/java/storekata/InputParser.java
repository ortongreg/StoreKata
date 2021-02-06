package storekata;

import storekata.models.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    private static final String ORDER_PATTERN_STR = "(.*),\\sbought\\s(today|(in\\s\\d+\\sdays\\stime))";
    private static final Pattern ORDER_PATTERN = Pattern.compile(ORDER_PATTERN_STR);

    private static final String PURCHASE_DAY_PATTERN_STR = "in\\s(\\d+)\\sdays\\stime";
    private static final Pattern PURCHASE_DAY_PATTERN = Pattern.compile(PURCHASE_DAY_PATTERN_STR);

    private static final String ITEM_PATTERN_STR = "(a|\\d+).*";
    private static final Pattern ITEM_PATTERN = Pattern.compile(ITEM_PATTERN_STR);

    public Order parse(String order){
        Matcher matcher = ORDER_PATTERN.matcher(order);
        matcher.matches();

        List<String> items = parseItems(matcher.group(1));

        String purchaseDayString = matcher.group(2);
        LocalDate purchaseDate = parsePurchaseDate(purchaseDayString);

        return new Order(purchaseDate, items);
    }

    private List<String> parseItems(String itemsString){
        List<String> result = new ArrayList<>();

        Matcher matcher = ITEM_PATTERN.matcher(itemsString);
        matcher.matches();

        int soupCount = canParseInt(matcher.group(1)) ?
                Integer.parseInt(matcher.group(1)) : 1;
        for(int i = 0; i< soupCount; i++){
            result.add("soup");
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

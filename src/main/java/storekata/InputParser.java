package storekata;

import storekata.models.Order;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputParser {
    private static final String ORDER_PATTERN_STR = ".*bought\\s(today|(in\\s\\d\\sdays\\stime))";
    private static final Pattern ORDER_PATTERN = Pattern.compile(ORDER_PATTERN_STR);

    private static final String PURCHASE_DAY_PATTERN_STR = "in\\s(\\d)\\sdays\\stime";
    private static final Pattern PURCHASE_DAY_PATTERN = Pattern.compile(PURCHASE_DAY_PATTERN_STR);

    public Order parse(String order){
        Matcher matcher = ORDER_PATTERN.matcher(order);
        matcher.matches();
        String purchaseDayString = matcher.group(1);

        Matcher purchaseDayMatcher = PURCHASE_DAY_PATTERN.matcher(purchaseDayString);
        int purchaseDateOffset = 0;
        if(purchaseDayMatcher.matches()){
            String purchaseDateOffsetStr = purchaseDayMatcher.group(1);
            purchaseDateOffset = Integer.parseInt(purchaseDateOffsetStr);
        }
        LocalDate purchaseDate = LocalDate.now().plusDays(purchaseDateOffset);

        return new Order(purchaseDate);
    }
}

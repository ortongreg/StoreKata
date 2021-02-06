package storekata;

import storekata.models.Order;

import java.time.LocalDate;

public class InputParser {

    public Order parse(String order){
        LocalDate purchaseDate = LocalDate.now();

        return new Order(purchaseDate);
    }
}

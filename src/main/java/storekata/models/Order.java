package storekata.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Order {

    private LocalDate purchaseDate;

    public Order(LocalDate purchaseDate){
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public List<String> getItems() {
        return Collections.singletonList("soup");
    }
}

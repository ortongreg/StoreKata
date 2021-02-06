package storekata.models;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private LocalDate purchaseDate;
    private List<String> items;

    public Order(LocalDate purchaseDate, List<String> items){
        this.purchaseDate = purchaseDate;
        this.items = items;
    }

    public LocalDate getPurchaseDate() { return purchaseDate; }

    public List<String> getItems() { return items; }
}

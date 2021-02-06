package storekata.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final LocalDate purchaseDate;
    private final List<Item> items;

    public Order(LocalDate purchaseDate, List<Item> items){
        this.purchaseDate = purchaseDate;
        this.items = items;
    }

    public LocalDate getPurchaseDate() { return purchaseDate; }

    public List<Item> getItems() { return new ArrayList<>(items); }
}

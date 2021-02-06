package storekata.models;

import java.time.LocalDate;

public class Order {

    private LocalDate purchaseDate;

    public Order(LocalDate purchaseDate){
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }
}

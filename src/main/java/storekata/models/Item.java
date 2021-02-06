package storekata.models;

import java.util.Objects;

public class Item {
    private final String name;
    public String getName(){ return name; };

    private final double cost;
    public double getCost(){ return cost; };

    public Item(String name, double cost){
        this.name = name;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.cost, cost) == 0 && Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost);
    }
}

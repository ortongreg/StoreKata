package storekata.repositories;

import storekata.models.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> allItems();
}

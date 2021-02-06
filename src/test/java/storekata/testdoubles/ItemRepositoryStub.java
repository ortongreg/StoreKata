package storekata.testdoubles;

import storekata.models.Item;
import storekata.repositories.ItemRepository;

import java.util.Arrays;
import java.util.List;

public class ItemRepositoryStub implements ItemRepository {

    @Override
    public List<Item> allItems() {
        return Arrays.asList(
                new Item("apple"),
                new Item("bread"),
                new Item("soup")
        );
    }
}

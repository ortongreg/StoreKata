package storekata.testdoubles;

import storekata.repositories.ItemRepository;

import java.util.Arrays;
import java.util.List;

public class ItemRepositoryStub implements ItemRepository {

    @Override
    public List<String> allItems() {
        return Arrays.asList("apple", "bread", "soup");
    }
}

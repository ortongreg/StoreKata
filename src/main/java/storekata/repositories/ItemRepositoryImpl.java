package storekata.repositories;

import storekata.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepositoryImpl implements ItemRepository{

    private static final Map<String, Item> items = new HashMap<String, Item>() {{
        put("apple", new Item("apple", .10));
        put("bread", new Item("bread", .80));
        put("milk", new Item("milk", 1.30));
        put("soup", new Item("soup", 0.65));
    }};

    @Override
    public List<Item> allItems() {
        return new ArrayList<>( items.values() );
    }
}

package com.layoutexample.Shopping_list_app;

import java.util.ArrayList;


public interface Repository {

    ItemModel findItemById(int id);
    ArrayList<ItemModel> findAllItems();
    void deleteItem(ItemModel itemModel);
    void save(ItemModel listItems);
    void deleteAll();
}

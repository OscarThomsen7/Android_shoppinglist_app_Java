package com.layoutexample.Shopping_list_app;

public class ItemModel {
    private int id;
    private String item;
    private int isChecked = 0;

    public ItemModel() {
        item = "";
        isChecked = 0;
    }

    public ItemModel(int id, String item, int isChecked) {
        this.id = id;
        this.item = item;
        this.isChecked = isChecked;
    }

    public int getId() {
        return id;
    }

    public ItemModel setId(int id) {
        this.id = id;
        return this;
    }
    public int getIsChecked(){
        return isChecked;
    }

    public ItemModel setIsChecked(int isChecked){
        this.isChecked = isChecked;
        return this;

    }
    public String getItem() {
        return item;
    }

    public ItemModel setItem(String item) {
        this.item = item;
        return this;
    }
}

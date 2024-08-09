package eason.example;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vendor {
    @JsonProperty("vendorname")
    private String vendorname;
    @JsonProperty("note")
    private String note;
    @JsonProperty("itemList")
    private final ArrayList<Item> itemList;

    public Vendor() {
        this.itemList = new ArrayList<>();
        this.note = "";
    }

    public void setName(String name) {
        this.vendorname = name;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void append_item(String name, int price) {
        Item item = new Item();
        item.setInfo(name, price);
        itemList.add(item);
    }

    @JsonIgnore
    public String getName() {
        return this.vendorname;
    }

    @JsonIgnore
    public String getNote() {
        return this.note;
    }

    @JsonIgnore
    public ArrayList<Item> getItemList() {
        return itemList;
    }
}

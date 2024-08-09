package eason.example;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("price")
    private int price;
    @JsonProperty("itemname")
    private String itemname;

    public Item() {
    }

    public void setInfo(String name, int price) {
        this.itemname = name;
        this.price = price;
    }

    @JsonIgnore
    public String getName() {
        return this.itemname;
    }

    @JsonIgnore
    public int getPrice() {
        return this.price;
    }
}

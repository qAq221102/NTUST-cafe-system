package eason.example;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Restaurant {
    @JsonProperty("restaurantname")
    private String restaurantname;
    @JsonProperty("vendorlist")
    private final ArrayList<Vendor> vendorlist;

    public Restaurant() {
        this.vendorlist = new ArrayList<>();
    }

    public void setName(String name) {
        this.restaurantname = name;
    }

    public void append_vendor(String name) {
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendorlist.add(vendor);
    }

    @JsonIgnore
    public String getName() {
        return this.restaurantname;
    }

    @JsonIgnore
    public ArrayList<Vendor> getVendorList() {
        return vendorlist;
    }
}

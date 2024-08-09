package eason.example;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class School {
    @JsonProperty("schoolname")
    private String schoolname;
    @JsonProperty("restaurantlist")
    private final ArrayList<Restaurant> restaurantlist;

    public School() {
        this.restaurantlist = new ArrayList<>();
    }

    public void setName(String name) {
        this.schoolname = name;
    }

    public void append_restaurant(String name) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurantlist.add(restaurant);
    }

    @JsonIgnore
    public String getName() {
        return this.schoolname;
    }

    @JsonIgnore
    public ArrayList<Restaurant> getRestaurantList() {
        return restaurantlist;
    }
}

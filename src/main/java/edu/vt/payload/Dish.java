package edu.vt.payload;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dish {
    private String title;
    private List<String> ingr;

    public Dish(String dishName, String ingredients) {
        this.title = dishName;
        this.ingr = new ArrayList<>(Arrays.asList(ingredients.split(",")));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngr() {
        return ingr;
    }

    public void setIngr(List<String> ingr) {
        this.ingr = ingr;
    }
}

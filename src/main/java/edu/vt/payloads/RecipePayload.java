/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.payloads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//This payload is used to send request to the API server.
public class RecipePayload {
    private String title;
    private List<String> ingr;

    public RecipePayload(String dishName, String ingredients) {
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

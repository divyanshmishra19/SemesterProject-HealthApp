/*
 * Created by Neel Gada on 2021.10.28
 * Copyright © 2021 Neel Gada. All rights reserved.
 */
package edu.vt.controllers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("sliderController")
@RequestScoped
public class SliderController {

    // The List contains image filenames, e.g., photo1.png, photo2.png, etc.
    private List<String> listOfSliderImageFilenames;

    /*
    The PostConstruct annotation is used on a method that needs to be executed after
    dependency injection is done to perform any initialization. The initialization 
    method init() is the first method invoked before this class is put into service. 
    */
    @PostConstruct
    public void init() {
        listOfSliderImageFilenames = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            listOfSliderImageFilenames.add("photo" + i + ".png");
        }
    }

    /*
    =============
    Getter Method
    =============
     */
    public List<String> getListOfSliderImageFilenames() {
        return listOfSliderImageFilenames;
    }

    /*
    ===============
    Instance Method
    ===============
    */
    public String description(String imageFilename) {

        String imageDescription = "";

        switch (imageFilename) {
            case "photo1.png":
                imageDescription = "Roasted Beet and Citrus Salad With Ricotta and Pistachio Vinaigrette";
                break;
            case "photo2.png":
                imageDescription = "Shrimp and Pasta Primavera";
                break;
            case "photo3.png":
                imageDescription = "Vanilla Cake";
                break;
            case "photo4.png":
                imageDescription = "Chicken with Sourdough-Mushroom Stuffing";
                break;
            case "photo5.png":
                imageDescription = "The Best Minestrone Soup Recipe";
                break;
            case "photo6.png":
                imageDescription = "Almond-Cardamom Baklava";
                break;
            case "photo7.png":
                imageDescription = "Lemon Pepper Dill Tilapia";
                break;
            case "photo8.png":
                imageDescription = "Pumpkin Pecan Cheesecake Cookie Bars";
                break;
            case "photo9.png":
                imageDescription = "Parmesan Crusted Chicken";
                break;
            case "photo10.png":
                imageDescription = "Mint Chocolate Ice Cream Sandwich";
                break;
            case "photo11.png":
                imageDescription = "Red Cabbage Salad with Roasted Cipollini Onions";
                break;
            case "photo12.png":
                imageDescription = "Buffalo Chicken Drumsticks with Blue Cheese Dip";
                break;
        }

        return imageDescription;
    }
}

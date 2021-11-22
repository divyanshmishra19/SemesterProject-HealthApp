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
            listOfSliderImageFilenames.add("photo" + i + ".jpg");
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
            case "photo1.jpg":
                imageDescription = "Roasted Beet and Citrus Salad With Ricotta and Pistachio Vinaigrette";
                break;
            case "photo2.jpg":
                imageDescription = "Shrimp and Pasta Primavera";
                break;
            case "photo3.jpg":
                imageDescription = "Vanilla Cake";
                break;
            case "photo4.jpg":
                imageDescription = "Chicken with Sourdough-Mushroom Stuffing";
                break;
            case "photo5.jpg":
                imageDescription = "The Best Minestrone Soup Recipe";
                break;
            case "photo6.jpg":
                imageDescription = "Almond-Cardamom Baklava";
                break;
            case "photo7.jpg":
                imageDescription = "Lemon Pepper Dill Tilapia";
                break;
            case "photo8.jpg":
                imageDescription = "Pumpkin Pecan Cheesecake Cookie Bars";
                break;
            case "photo9.jpg":
                imageDescription = "Parmesan Crusted Chicken";
                break;
            case "photo10.jpg":
                imageDescription = "Mint Chocolate Ice Cream Sandwich";
                break;
            case "photo11.jpg":
                imageDescription = "Red Cabbage Salad with Roasted Cipollini Onions";
                break;
            case "photo12.jpg":
                imageDescription = "Buffalo Chicken Drumsticks with Blue Cheese Dip";
                break;
        }

        return imageDescription;
    }
}

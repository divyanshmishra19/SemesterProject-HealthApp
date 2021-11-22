package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.FacadeBeans.RecipeFacade;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("recipeController")
@RequestScoped

public class RecipeController implements Serializable {

    private List<Recipe> listOfRecipes;
    private RecipeFacade recipeFacade;


    public List<Recipe> getListOfVideos() {
        if (listOfRecipes == null) {
            //listOfRecipes = recipeFacade.findAll();
        }
        return listOfRecipes;
    }
}

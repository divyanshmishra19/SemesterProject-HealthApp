/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.FacadeBeans.RecipeFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("recipeController")
@SessionScoped

public class RecipeController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private List<Recipe> listOfRecipes;
    private Recipe selected;
    private UserRecipe userRecipe;

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    RecipeFacade and UserRecipeFacade bean into the instance variable 'recipeFacade' and 'userRecipeFacade' after it is instantiated at runtime.
     */
    @EJB
    private RecipeFacade recipeFacade;

    @EJB
    private UserRecipeFacade userRecipeFacade;


    /*
    ================
    Instance Methods
    ================
    */
    public Recipe getSelected() {
        return selected;
    }

    public void setSelected(Recipe selected) {
        this.selected = selected;
    }

    public List<Recipe> getListOfRecipes() {
        if (listOfRecipes == null) {
            listOfRecipes = recipeFacade.findAll();
        }
        return listOfRecipes;
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public void setListOfRecipes(List<Recipe> listOfRecipes) {
        this.listOfRecipes = listOfRecipes;
    }

    public UserRecipe getUserRecipe() {
        return userRecipe;
    }

    public void setUserRecipe(UserRecipe userRecipe) {
        this.userRecipe = userRecipe;
    }

    public RecipeFacade getRecipeFacade() {
        return recipeFacade;
    }

    public void setRecipeFacade(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    public UserRecipeFacade getUserRecipeFacade() {
        return userRecipeFacade;
    }

    public void setUserRecipeFacade(UserRecipeFacade userRecipeFacade) {
        this.userRecipeFacade = userRecipeFacade;
    }


    /*
     **************************************
     *   Unselect Selected Recipe Object  *
     **************************************
     */
    public void unselect() {
        selected = null;
    }

    /*
     *************************************
     *   Cancel and Display List.xhtml   *
     *************************************
     */
    public String cancel() {
        // Unselect previously selected recipe object if any
        selected = null;
        return "/recipe/List?faces-redirect=true";
    }

    /*
     *********************************************
     *   ADD the selected recipe to UserRecipe   *
     *********************************************
     */
    public void share() {
        userRecipe = new UserRecipe();
        User signedIn = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(signedIn==null)
        {
            Methods.showMessage("Information", "Unable to Share!", "To share a recipe, user must be logged in");
            return;
        }

        //setting name of the dish
        userRecipe.setName(selected.getName());

        //setting total calories of the dish
        userRecipe.setCalories(selected.getCalories());

        //setting fat split-up
        userRecipe.setFatTotal(selected.getFatTotal());
        userRecipe.setFatTrans(selected.getFatTrans());
        userRecipe.setFatPoly(selected.getFatPoly());
        userRecipe.setFatMono(selected.getFatMono());
        userRecipe.setFatSat(selected.getFatSat());

        //setting macronutrients
        userRecipe.setCarbs(selected.getCarbs());
        userRecipe.setProtein(selected.getProtein());

        //setting macronutrients calorie contribution
        userRecipe.setCarbCal(selected.getCarbCal());
        userRecipe.setProteinCal(selected.getProteinCal());
        userRecipe.setFatCal(selected.getFatCal());

        //setting micronutrients and minerals
        userRecipe.setSodium(selected.getSodium());
        userRecipe.setCalcium(selected.getCalcium());
        userRecipe.setPotassium(selected.getPotassium());
        userRecipe.setMagnesium(selected.getMagnesium());
        userRecipe.setIron(selected.getIron());
        userRecipe.setZinc(selected.getZinc());

        //setting ingredients and diet labels
        userRecipe.setDietLabels(selected.getDietLabels());
        userRecipe.setIngredients(selected.getIngredients());

        userRecipe.setUserId(signedIn);

        Methods.preserveMessages();
        userRecipeFacade.edit(userRecipe);
        Methods.showMessage("Information", "Shared!", "Public recipe was successfully shared.");
    }
}

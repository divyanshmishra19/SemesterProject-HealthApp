package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.FacadeBeans.RecipeFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("recipeController")
@SessionScoped

public class RecipeController implements Serializable {

    private List<Recipe> listOfRecipes;
    private Recipe selected;
    private UserRecipe userRecipe;

    @EJB
    private RecipeFacade recipeFacade;

    @EJB
    private UserRecipeFacade userRecipeFacade;


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

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected movie object if any
        selected = null;
        return "/recipe/List?faces-redirect=true";
    }

    public void share() {
        System.out.println("AAA");
        userRecipe = new UserRecipe();
        User signedIn = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(signedIn==null)
        {
            Methods.showMessage("Information", "Unable to Share!", "To share a recipe, user must be logged in");
            return;
        }

        userRecipe.setCalcium(selected.getCalcium());
        userRecipe.setCalories(selected.getCalories());
        userRecipe.setCarbCal(selected.getCarbCal());
        userRecipe.setCarbs(selected.getCarbs());
        userRecipe.setDietLabels(selected.getDietLabels());
        userRecipe.setFatCal(selected.getFatCal());
        //and all the rest of the fields..........
        userRecipe.setUserId(signedIn);

        Methods.preserveMessages();
        userRecipeFacade.edit(userRecipe);
        Methods.showMessage("Information", "Shared!", "Public recipe was successfully shared.");
    }
}

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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("recipeController")
@RequestScoped

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

    public void shareRecipeToUserRecipe(){
        // TODO: Implement this feature
    }
    /*
    *****************************
    Prepare to Create a New Recipe
    *****************************
    */
    public void prepareCreate() {
        /*
        Instantiate a new Recipe object and store its object reference into
        instance variable 'selected'. The Recipe class is defined in Recipe.java
         */
        selected = new Recipe();
    }


    /*
    **********************************
    CREATE a New Recipe in the Database
    **********************************
     */
    public void create() {
        Methods.preserveMessages();


        /*
        An enum is a special Java type used to define a group of constants.
        The constants CREATE, DELETE and UPDATE are defined as follows in JsfUtil.java

                public enum PersistAction {
                    CREATE,
                    DELETE,
                    UPDATE
                }
         */

        /*
         The object reference of the recipe to be created is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(JsfUtil.PersistAction.CREATE,"Recipe was Successfully Created!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;      // Remove selection
            listOfRecipes = null; // Invalidate listOfRecipes to trigger re-query.
        }
    }

    /*
    *************************************
    UPDATE Selected Recipe in the Database
    *************************************
     */
    public void update() {
        Methods.preserveMessages();
        /*
         The object reference of the recipe to be updated is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(JsfUtil.PersistAction.UPDATE,"Recipe was Successfully Updated!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null;       // Remove selection
            listOfRecipes = null;  // Invalidate listOfRecipes to trigger re-query.
        }
    }

    /*
     *************************************************
     *   DELETE Selected Recipe from the Database    *
     *************************************************
     */
    public void destroy() {
        Methods.preserveMessages();
        /*
         The object reference of the recipe to be deleted is stored in the instance variable 'selected'
         See the persist method below.
         */
        persist(JsfUtil.PersistAction.DELETE,"Recipe was Successfully Deleted!");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;       // Remove selection
            listOfRecipes = null;  // Invalidate listOfRecipes to trigger re-query.
        }
    }

    /*
     **********************************************************************************************
     *   Perform CREATE, UPDATE (EDIT), and DELETE (DESTROY, REMOVE) Operations in the Database   *
     **********************************************************************************************
     */
    /**
     * @param persistAction refers to CREATE, UPDATE (Edit) or DELETE action
     * @param successMessage displayed to inform the user about the result
     */
    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    /*
                     -------------------------------------------------
                     Perform CREATE or EDIT operation in the database.
                     -------------------------------------------------
                     The edit(selected) method performs the SAVE (STORE) operation of the "selected"
                     object in the database regardless of whether the object is a newly
                     created object (CREATE) or an edited (updated) object (EDIT or UPDATE).

                     RecipeFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    recipeFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     RecipeFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    recipeFacade.remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex,"A persistence error occurred.");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex,"A persistence error occurred.");
            }
        }
    }
}

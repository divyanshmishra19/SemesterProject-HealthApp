package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserRecipeConsumed;
import edu.vt.FacadeBeans.UserRecipeConsumedFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userRecipeController")
@RequestScoped

public class UserRecipeController implements Serializable {

    private List<UserRecipe> listOfUserRecipes;
    private UserRecipe selected;
    UserRecipeConsumed recipeConsumed;

    @EJB
    UserRecipeFacade userRecipeFacade;

    @EJB
    UserRecipeConsumedFacade userRecipeConsumedFacade;

    public List<UserRecipe> getListOfUserRecipes() {
        if (listOfUserRecipes == null) {
            listOfUserRecipes = userRecipeFacade.findAll();
        }
        return listOfUserRecipes;
    }

    public void setListOfUserRecipes(List<UserRecipe> listOfUserRecipes) {
        this.listOfUserRecipes = listOfUserRecipes;
    }

    public UserRecipe getSelected() {
        return selected;
    }

    public void setSelected(UserRecipe selected) {
        this.selected = selected;
    }

    public UserRecipeFacade getUserRecipeFacade() {
        return userRecipeFacade;
    }

    public void setUserRecipeFacade(UserRecipeFacade userRecipeFacade) {
        this.userRecipeFacade = userRecipeFacade;
    }

    public UserRecipeConsumed getRecipeConsumed() {
        return recipeConsumed;
    }

    public void setRecipeConsumed(UserRecipeConsumed recipeConsumed) {
        this.recipeConsumed = recipeConsumed;
    }

    public UserRecipeConsumedFacade getUserRecipeConsumedFacade() {
        return userRecipeConsumedFacade;
    }

    public void setUserRecipeConsumedFacade(UserRecipeConsumedFacade userRecipeConsumedFacade) {
        this.userRecipeConsumedFacade = userRecipeConsumedFacade;
    }

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected userRecipe object if any
        selected = null;
        return "/health/List?faces-redirect=true";
    }


    public void prepareCreate() {
        /*
        Instantiate a new Video object and store its object reference into
        instance variable 'selected'. The UserRecipe class is defined in UserRecipe.java
         */
        selected = new UserRecipe();
    }


    public void create() {
        //this function needs modification!
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.CREATE, "Public Video was successfully created.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserRecipes = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void destroy() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.DELETE, "User Recipe was successfully deleted.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserRecipes = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    /*
     **********************************************************************************************
     *   Perform CREATE, UPDATE (EDIT), and DELETE (DESTROY, REMOVE) Operations in the Database   *
     **********************************************************************************************
     */

    /**
     * @param persistAction  refers to CREATE, UPDATE (Edit) or DELETE action
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

                     PublicVideoFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    userRecipeFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     PublicVideoFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    userRecipeFacade.remove(selected);
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
                    JsfUtil.addErrorMessage(ex, "A persistence error occurred.");
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, "A persistence error occurred.");
            }
        }
    }

    public void addToProgress() {
        Date todaysDate = new Date(System.currentTimeMillis());
        recipeConsumed = new UserRecipeConsumed();

        recipeConsumed.setRecipeId(selected);
        recipeConsumed.setUserId(selected.getUserId());
        recipeConsumed.setDate(todaysDate);

        Methods.preserveMessages();
        userRecipeConsumedFacade.edit(recipeConsumed);
        Methods.showMessage("Information", "Progress Updated!",
                "Meal was successfully added to your daily progress!.");
    }
}

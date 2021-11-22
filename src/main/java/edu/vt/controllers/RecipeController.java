package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.FacadeBeans.RecipeFacade;
import edu.vt.globals.Methods;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.vt.controllers.util.JsfUtil;

@Named("recipeController")
@RequestScoped

public class RecipeController implements Serializable {

    private List<Recipe> listOfRecipes;
    private boolean recipeDataChanged;
    private RecipeFacade recipeFacade;
    private Recipe selected;

    public List<Recipe> getListOfVideos() {
        if (listOfRecipes == null) {
            //listOfRecipes = recipeFacade.findAll();
        }
        return listOfRecipes;
    }


    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected movie object if any
        selected = null;
        return "/videos/List?faces-redirect=true";
    }


    public void prepareCreate() {
        /*
        Instantiate a new Video object and store its object reference into
        instance variable 'selected'. The Movie class is defined in PublicVideo.java
         */
        selected = new Recipe();
    }


    public void create() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.CREATE,"Public Video was successfully created.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfRecipes = null;    // Invalidate listOfMovies to trigger re-query.
            recipeDataChanged = true;
        }
    }

    public void update() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.UPDATE,"Public Video was successfully updated.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfRecipes = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void destroy() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.DELETE,"Public Video was successfully deleted.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;        // Remove selection
            listOfRecipes = null;    // Invalidate listOfMovies to trigger re-query.
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

                     PublicVideoFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    recipeFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     PublicVideoFacade inherits the remove(selected) method from the AbstractFacade class.
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

    /*
     ******************************************
     *   Display the Search Results JSF Page  *
     ******************************************
     */
//    public String search(Integer type) {
//        // Set search type given as input parameter
//        searchType = type;
//
//        // Unselect previously selected video if any before showing the search results
//        selected = null;
//
//        // Invalidate list of search items to trigger re-query.
//        searchItems = null;
//
//        return "/publicVideo/SearchResults?faces-redirect=true";
//    }
}

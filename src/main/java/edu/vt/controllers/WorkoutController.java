package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.Workout;
import edu.vt.FacadeBeans.WorkoutFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Methods;

import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named("workoutController")
@RequestScoped

public class WorkoutController implements Serializable {

    private List<Workout> listOfWorkouts;
    private WorkoutFacade workoutFacade;
    private Workout selected;

    public Workout getSelected() {
        return selected;
    }

    public void setSelected(Workout selected) {
        this.selected = selected;
    }

    public List<Workout> getListOfWorkouts() {
        if (listOfWorkouts == null) {
            List<Workout> wlist = new ArrayList<>();
            wlist.add(new Workout(5F,"Pull Ups", "Strength"));
            wlist.add(new Workout(0.5F,"Walking", "Aerobic"));
            wlist.add(new Workout(2F,"Jogging", "Aerobic"));
            return wlist;
        }
        return listOfWorkouts;
    }

    public String getUrlOfVideoToPlay () {
        return getUrlOfVideoToPlay();
    }

    public void setUrlOfVideoToPlay(String urlOfVideoToPlay) {
        selected.setUrlOfWorkoutToPlay(urlOfVideoToPlay);
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
        selected = new Workout();
    }


    public void create() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.CREATE,"Public Video was successfully created.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfWorkouts = null;    // Invalidate listOfMovies to trigger re-query.

        }
    }

    public void update() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.UPDATE,"Public Video was successfully updated.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfWorkouts = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void destroy() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.DELETE,"Public Video was successfully deleted.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;        // Remove selection
            listOfWorkouts = null;    // Invalidate listOfMovies to trigger re-query.
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
                    workoutFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     PublicVideoFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    workoutFacade.remove(selected);
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
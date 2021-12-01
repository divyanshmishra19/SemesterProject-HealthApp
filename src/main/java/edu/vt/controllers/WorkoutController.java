package edu.vt.controllers;

import edu.vt.EntityBeans.*;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.FacadeBeans.WorkoutFacade;
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

@Named("workoutController")
@RequestScoped

public class WorkoutController implements Serializable {

    private List<Workout> listOfWorkouts;
    private Workout selected;
    private UserWorkout userWorkout;
    private String youtubeTutorialVideoId;

    @EJB
    private WorkoutFacade workoutFacade;

    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    public Workout getSelected() {
        return selected;
    }

    public void setSelected(Workout selected) {
        this.selected = selected;
    }

    public List<Workout> getListOfWorkouts() {
        if (listOfWorkouts == null) {
            listOfWorkouts = workoutFacade.findAll();
        }
        return listOfWorkouts;
    }

    public void unselect() {
        selected = null;
    }
    public void setListOfWorkouts(List<Workout> listOfWorkouts) {
        this.listOfWorkouts = listOfWorkouts;
    }

    public UserWorkout getUserWorkout() {
        return userWorkout;
    }

    public void setUserWorkout(UserWorkout userWorkout) {
        this.userWorkout = userWorkout;
    }

    public String getYoutubeTutorialVideoId() {
        return youtubeTutorialVideoId;
    }

    public void setYoutubeTutorialVideoId(String youtubeTutorialVideoId) {
        selected.setYoutubeTutorialVideoId(youtubeTutorialVideoId);
    }

    public WorkoutFacade getWorkoutFacade() {
        return workoutFacade;
    }

    public void setWorkoutFacade(WorkoutFacade workoutFacade) {
        this.workoutFacade = workoutFacade;
    }

    public UserWorkoutFacade getUserWorkoutFacade() {
        return userWorkoutFacade;
    }

    public void setUserWorkoutFacade(UserWorkoutFacade userWorkoutFacade) {
        this.userWorkoutFacade = userWorkoutFacade;
    }

    public String getUrlOfVideoToPlay(){
        return youtubeTutorialVideoId;
    }

    public String cancel() {
        // Unselect previously selected movie object if any
        selected = null;
        return "/workout/List?faces-redirect=true";
    }

    public void share() {
        userWorkout = new UserWorkout();
        User signedIn = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(signedIn==null)
        {
            Methods.showMessage("Information", "Unable to Share!", "To share a workout routine, user must be logged in");
            return;
        }

        userWorkout.setYoutubeTutorialVideoId(selected.getYoutubeTutorialVideoId());
        userWorkout.setBurnRate(selected.getBurnRate());
        userWorkout.setCategory(selected.getCategory());
        userWorkout.setName(selected.getName());
        userWorkout.setUserId(signedIn);

        Methods.preserveMessages();
        userWorkoutFacade.edit(userWorkout);
        Methods.showMessage("Information", "Shared!", "Public workout routine was successfully shared.");
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
            listOfWorkouts = null;  // Invalidate listOfRecipes to trigger re-query.
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
                    workoutFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     RecipeFacade inherits the remove(selected) method from the AbstractFacade class.
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
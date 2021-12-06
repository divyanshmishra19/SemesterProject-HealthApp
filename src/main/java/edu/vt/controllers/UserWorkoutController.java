package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserWorkout;
import edu.vt.EntityBeans.UserWorkoutDone;
import edu.vt.FacadeBeans.UserWorkoutDoneFacade;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userWorkoutController")
@SessionScoped

public class UserWorkoutController implements Serializable {

    private List<UserWorkout> listOfUserWorkouts;
    private UserWorkout selected;
    private UserWorkoutDone workoutDone;
    private Double duration;
    private String youtubeTutorialVideoId;

    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    @EJB
    private UserWorkoutDoneFacade workoutDoneFacade;

    public List<UserWorkout> getListOfUserWorkouts() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        return userWorkoutFacade.findWorkoutsByUserId(signedInUser.getId());
    }

    public void setListOfUserWorkouts(List<UserWorkout> listOfUserWorkouts) {
        this.listOfUserWorkouts = listOfUserWorkouts;
    }

    public UserWorkout getSelected() {
        return selected;
    }

    public void setSelected(UserWorkout selected) {
        this.selected = selected;
    }

    public UserWorkoutDone getWorkoutDone() {
        return workoutDone;
    }

    public void setWorkoutDone(UserWorkoutDone workoutDone) {
        this.workoutDone = workoutDone;
    }

    public UserWorkoutFacade getUserWorkoutFacade() {
        return userWorkoutFacade;
    }

    public void setUserWorkoutFacade(UserWorkoutFacade userWorkoutFacade) {
        this.userWorkoutFacade = userWorkoutFacade;
    }

    public UserWorkoutDoneFacade getWorkoutDoneFacade() {
        return workoutDoneFacade;
    }

    public void setWorkoutDoneFacade(UserWorkoutDoneFacade workoutDoneFacade) {
        this.workoutDoneFacade = workoutDoneFacade;
    }

        public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getYoutubeTutorialVideoId() {
        return youtubeTutorialVideoId;
    }

    public void setYoutubeTutorialVideoId(String youtubeTutorialVideoId) {
        selected.setYoutubeTutorialVideoId(youtubeTutorialVideoId);
    }

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected movie object if any
        selected = null;
        return "/userWorkout/List?faces-redirect=true";
    }

    public void prepareCreate() {
        /*
        Instantiate a new UserWorkout object and store its object reference into
        instance variable 'selected'. The UserWorkout class is defined in UserWorkout.java
         */
        selected = new UserWorkout();
    }


    public void create() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        selected.setUserId(signedInUser);
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.CREATE, "User Workout Routine was successfully created.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserWorkouts = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void update() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        selected.setUserId(signedInUser);
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.UPDATE, "User Workout Routine successfully updated.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The UPDATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserWorkouts = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void destroy() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        selected.setUserId(signedInUser);
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.DELETE, "User Workout Routine successfully deleted.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserWorkouts = null;    // Invalidate listOfMovies to trigger re-query.
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
                    userWorkoutFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     PublicVideoFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    userWorkoutFacade.remove(selected);
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
        workoutDone = new UserWorkoutDone();

        workoutDone.setWorkoutId(selected);
        workoutDone.setDate(todaysDate);

        Double burnRate = selected.getBurnRate();
        int durationInt = duration.intValue();
        workoutDone.setDuration(durationInt);
        workoutDone.setCalories((int)(durationInt * burnRate));

        Methods.preserveMessages();
        workoutDoneFacade.edit(workoutDone);
        Methods.showMessage("Information", "Progress Updated!",
                "Workout Routine was successfully added to your daily progress!.");
    }
}

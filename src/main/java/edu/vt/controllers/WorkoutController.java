/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.*;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.FacadeBeans.WorkoutFacade;
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

@Named("workoutController")
@SessionScoped

public class WorkoutController implements Serializable {
    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private List<Workout> listOfWorkouts;
    private Workout selected;
    private UserWorkout userWorkout;
    private String youtubeTutorialVideoId;

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    WorkoutFacade bean into the instance variable 'workoutFacade' after it is instantiated at runtime.
     */
    @EJB
    private WorkoutFacade workoutFacade;

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserWorkoutFacade bean into the instance variable 'userWorkoutFacade' after it is instantiated at runtime.
     */
    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

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

    /*
    ================
    Instance Methods
    ================
    */

    /*
     *************************************
     *   Cancel and Display List.xhtml   *
     *************************************
     */
    public String cancel() {
        // Unselect previously selected workout object if any
        selected = null;
        return "/workout/List?faces-redirect=true";
    }


    /*
     **************************************************
     *   Shares the selected workout to userWorkout   *
     **************************************************
     */
    public void share() {
        userWorkout = new UserWorkout();
        User signedIn = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if (signedIn == null) {
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
}
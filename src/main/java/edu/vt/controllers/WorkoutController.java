package edu.vt.controllers;

import edu.vt.EntityBeans.*;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.FacadeBeans.WorkoutFacade;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
}
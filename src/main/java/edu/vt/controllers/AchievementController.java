package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.*;
import edu.vt.globals.Constants;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;

@Named("achievementController")
@SessionScoped
public class AchievementController implements Serializable {

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserRecipeFacade and UserWorkoutFacade bean into the instance variables 'userRecipeFacade' and 'userWorkoutFacade'
    after it is instantiated at runtime.
     */
    @EJB
    private UserRecipeConsumedFacade userRecipeConsumedFacade;

    @EJB
    private UserWorkoutDoneFacade userWorkoutDoneFacade;

    @EJB
    private UserFacade userFacade;

    public String dailyCalorieIntakeGoal()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
        Double expectedCalories = userFacade.getUserCalorieIntake(signedInUser.getId());

        if(calories>expectedCalories)
            return "true";
        else
            return "false";
    }

    public String dailyCalorieBurnGoal()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        Date todaysDate = new Date(System.currentTimeMillis());
        Integer caloriesBurned = userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());

        if(caloriesBurned>desiredCaloriesBurned)
            return "true";
        else
            return "false";
    }

    public int longestCalorieBurnStreak()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        Date todaysDate = new Date(System.currentTimeMillis());

        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());
        int streak=0, maxStreak=0;
        Integer caloriesBurned = 0;
        while(caloriesBurned!=0.0)
        {
            caloriesBurned = userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
            todaysDate = new Date(todaysDate.getTime() - Constants.MILLIS_IN_A_DAY);

            if(caloriesBurned>desiredCaloriesBurned)
                streak++;
            else
                streak=0;
            maxStreak = Math.max(streak, maxStreak);
        }
        return maxStreak;
    }

    public int longestCalorieIntakeStreak()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        Date todaysDate = new Date(System.currentTimeMillis());

        Double desiredCaloriesIntake = userFacade.getUserCalorieIntake(signedInUser.getId());
        int streak=0, maxStreak=0;
        Double caloriesConsumed = 0.0;
        while(caloriesConsumed!=null)
        {
            caloriesConsumed = userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
            todaysDate = new Date(todaysDate.getTime() - Constants.MILLIS_IN_A_DAY);

            if(caloriesConsumed>desiredCaloriesIntake)
                streak++;
            else
                streak=0;
            maxStreak = Math.max(streak, maxStreak);
        }
        return maxStreak;
    }
}

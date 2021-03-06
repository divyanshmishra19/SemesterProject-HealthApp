/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
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
import java.util.Map;

@Named("achievementController")
@SessionScoped
public class AchievementController implements Serializable {

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserRecipeConsumedFacade, UserWorkoutDoneFacade, UserFacade bean into the instance variables
    'userRecipeConsumedFacade', 'userWorkoutDoneFacade' and 'userFacade'
    after it is instantiated at runtime.
     */
    @EJB
    private UserRecipeConsumedFacade userRecipeConsumedFacade;

    @EJB
    private UserWorkoutDoneFacade userWorkoutDoneFacade;

    @EJB
    private UserFacade userFacade;

    /*
    ******************************************************
    Checks of daily calorie intake goal is achieved or not
    ******************************************************
    */
    public boolean dailyCalorieIntakeGoal() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
        Double expectedCalories = userFacade.getUserCalorieIntake(signedInUser.getId());

        return calories > expectedCalories;
    }

    /*
    ****************************************************
    Checks of daily calorie burn goal is achieved or not
    ****************************************************
    */
    public boolean dailyCalorieBurnGoal() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        Date todaysDate = new Date(System.currentTimeMillis());
        Integer caloriesBurned = userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());

        return caloriesBurned > desiredCaloriesBurned;
    }

    /*
    *******************************************
    Checks the longest streak of goals achieved
    *******************************************
    */
    public String longestCalorieBurnStreak() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        Date todaysDate = new Date(System.currentTimeMillis());

        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());
        int streak = 0, maxStreak = 0;
        Integer caloriesBurned = 0;
        for (int i = 0; i < 30; i++) {
            caloriesBurned = userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
            todaysDate = new Date(todaysDate.getTime() - Constants.MILLIS_IN_A_DAY);

            if (caloriesBurned > desiredCaloriesBurned)
                streak++;
            else
                streak = 0;
            maxStreak = Math.max(streak, maxStreak);
        }
        return maxStreak + (maxStreak == 1 ? " day " : " days ") + burnSymbols(maxStreak);
    }

    /*
    *******************************************
    Checks the longest streak of goals achieved
    *******************************************
    */
    public String longestCalorieIntakeStreak() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        Date todaysDate = new Date(System.currentTimeMillis());

        Double desiredCaloriesIntake = userFacade.getUserCalorieIntake(signedInUser.getId());
        int streak = 0, maxStreak = 0;
        Double caloriesConsumed = 0.0;
        for (int i = 0; i < 30; i++) {
            caloriesConsumed = userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
            todaysDate = new Date(todaysDate.getTime() - Constants.MILLIS_IN_A_DAY);

            if (caloriesConsumed > desiredCaloriesIntake)
                streak++;
            else
                streak = 0;
            maxStreak = Math.max(streak, maxStreak);
        }
        return maxStreak + (maxStreak == 1 ? " day " : " days ") + burnSymbols(maxStreak);
    }

    /*
    ********************************
    Adds Symbols is Streak is broken
    ********************************
    */
    private String burnSymbols(int streak) {
        return streak == 0 ? "☹" : "\uD83D\uDD25";
    }
}

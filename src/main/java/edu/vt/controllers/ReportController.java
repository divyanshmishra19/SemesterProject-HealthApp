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
import java.util.*;

@Named("reportController")
@SessionScoped
public class ReportController implements Serializable {

    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
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

    //Daily Calories Pie Chart
    public String getDailyCalorieChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
        Double expectedCalories = userFacade.getUserCalorieIntake(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (calories < expectedCalories) {
            calories = (calories / expectedCalories) * 100;
            Double caloriesLeft = 100 - calories;
            pieChartUrl.append(Constants.DATA + calories + "," + caloriesLeft);
            pieChartUrl.append(Constants.LABEL + "Calories Consumed|Calories left");
        } else {
            expectedCalories = (expectedCalories / calories) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + "Expected Calories|Calories Extra");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getDailyFatsChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeConsumedFacade.getFats(todaysDate, signedInUser.getId());
        String fatLabels = "Saturated|Trans|Monounsaturated|Polyunsaturated";

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double fat : fats)
            data += fat + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatLabels);
        return pieChartUrl.toString();
    }

    //Macronutrients Calorie Contribution
    public String getDailyCalorieSplit() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> calorieSplit = userRecipeConsumedFacade.getDailyCalorieSplit(todaysDate, signedInUser.getId());
        String fatLabels = "Carbs|Fats|Proteins";

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD");
        String data = Constants.DATA;
        for (Double macro : calorieSplit)
            data += macro + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatLabels);
        return pieChartUrl.toString();
    }

    //User Workout
    public String getDailyWorkout() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        Integer caloriesBurned = userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (caloriesBurned < desiredCaloriesBurned) {
            Double calories = (caloriesBurned / desiredCaloriesBurned) * 100;
            Double caloriesLeft = 100 - calories;
            pieChartUrl.append(Constants.DATA + calories + "," + caloriesLeft);
            pieChartUrl.append(Constants.LABEL + "Calories Burned|Calories left to Burn");
        } else {
            Double expectedCalories = (desiredCaloriesBurned / caloriesBurned) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + "Burnt Calories|Calories Burnt Extra");
        }
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getDailyMicronutrients() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> nutrients = userRecipeConsumedFacade.getMicronutrients(todaysDate, signedInUser.getId());
        String nutrientsLabels = "Sodium|Calcium|Magnesium|Potassium|Iron|Zinc";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for (Double nutrient : nutrients)
            data += nutrient + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + nutrientsLabels);
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getDailyCategoryWiseCalories() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> workoutCategoryWiseCalories = userWorkoutDoneFacade.getCategoryWiseCalories(todaysDate, signedInUser.getId());
        String categoryLabels = "Calisthenics|Cardio|Strength|HIIT";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double calories : workoutCategoryWiseCalories)
            data += calories + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + categoryLabels);
        return barChartUrl.toString();
    }

    //Daily Calories Pie Chart
    public String getWeeklyCalorieChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        Double calories = 0.0;
        Double expectedCalories = 0.0;
        for (int i = 0; i < 7; i++) {
            calories += userRecipeConsumedFacade.getTotalDailyCalories(todaysDate, signedInUser.getId());
            expectedCalories += userFacade.getUserCalorieIntake(signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);
        }

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (calories < expectedCalories) {
            calories = (calories / expectedCalories) * 100;
            Double caloriesLeft = 100 - calories;
            pieChartUrl.append(Constants.DATA + calories + "," + caloriesLeft);
            pieChartUrl.append(Constants.LABEL + "Calories Consumed|Calories left");
        } else {
            expectedCalories = (expectedCalories / calories) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + "Expected Calories|Calories Over");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getWeeklyFatsChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        List<Double> fats = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        String fatLabels = "Saturated|Trans|Monounsaturated|Polyunsaturated";
        for (int i = 0; i < 7; i++) {
            List<Double> dailyFats = userRecipeConsumedFacade.getFats(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            fats.add(0, fats.get(0) + dailyFats.get(0));
            fats.add(1, fats.get(1) + dailyFats.get(1));
            fats.add(2, fats.get(2) + dailyFats.get(2));
            fats.add(3, fats.get(3) + dailyFats.get(3));
        }

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double fat : fats)
            data += fat + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatLabels);
        return pieChartUrl.toString();
    }

    //User Workout
    public String getWeeklyWorkout() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        Integer caloriesBurned = 0;
        Double desiredCaloriesBurned = 0.0;

        for (int i = 0; i < 7; i++) {
            caloriesBurned += userWorkoutDoneFacade.getDailyWorkoutCalories(todaysDate, signedInUser.getId());
            desiredCaloriesBurned += userFacade.getUserWorkoutCalories(signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);
        }

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (caloriesBurned < desiredCaloriesBurned) {
            Double calories = (caloriesBurned / desiredCaloriesBurned) * 100;
            Double caloriesLeft = 100 - calories;
            pieChartUrl.append(Constants.DATA + calories + "," + caloriesLeft);
            pieChartUrl.append(Constants.LABEL + "Calories Burned|Calories left to Burn");
        } else {
            Double expectedCalories = (desiredCaloriesBurned / caloriesBurned) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + "Burnt Calories|Calories Burnt Extra");
        }
        return pieChartUrl.toString();
    }

    //Macronutrients Calorie Contribution
    public String getWeeklyCalorieSplit() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        String fatLabels = "Carbs|Fats|Proteins";
        List<Double> calorieSplit = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
        for (int i = 0; i < 7; i++) {
            List<Double> dailyalorieSplit = userRecipeConsumedFacade.getDailyCalorieSplit(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            calorieSplit.add(0, calorieSplit.get(0) + dailyalorieSplit.get(0));
            calorieSplit.add(1, calorieSplit.get(1) + dailyalorieSplit.get(1));
            calorieSplit.add(2, calorieSplit.get(2) + dailyalorieSplit.get(2));
        }


        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD");
        String data = Constants.DATA;
        for (Double macro : calorieSplit)
            data += macro + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatLabels);
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getWeeklyMicronutrients() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        List<Double> nutrients = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
        String nutrientsLabels = "Sodium|Calcium|Magnesium|Potassium|Iron|Zinc";
        for (int i = 0; i < 7; i++) {
            List<Double> dailyNutrients = userRecipeConsumedFacade.getMicronutrients(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            nutrients.add(0, nutrients.get(0) + dailyNutrients.get(0));
            nutrients.add(1, nutrients.get(1) + dailyNutrients.get(1));
            nutrients.add(2, nutrients.get(2) + dailyNutrients.get(2));
            nutrients.add(3, nutrients.get(3) + dailyNutrients.get(3));
            nutrients.add(4, nutrients.get(4) + dailyNutrients.get(4));
            nutrients.add(5, nutrients.get(5) + dailyNutrients.get(5));
        }

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for (Double nutrient : nutrients)
            data += nutrient + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + nutrientsLabels);
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getWeeklyCategoryWiseCalories() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());

        List<Double> workoutCategoryWiseCalories = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0));
        String categoryLabels = "Calisthenics|Cardio|Strength|HIIT";
        for (int i = 0; i < 7; i++) {
            List<Double> dailyWorkoutCategoryWiseCalories = userWorkoutDoneFacade.getCategoryWiseCalories(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            workoutCategoryWiseCalories.add(0, workoutCategoryWiseCalories.get(0) + dailyWorkoutCategoryWiseCalories.get(0));
            workoutCategoryWiseCalories.add(1, workoutCategoryWiseCalories.get(1) + dailyWorkoutCategoryWiseCalories.get(1));
            workoutCategoryWiseCalories.add(2, workoutCategoryWiseCalories.get(2) + dailyWorkoutCategoryWiseCalories.get(2));
            workoutCategoryWiseCalories.add(3, workoutCategoryWiseCalories.get(3) + dailyWorkoutCategoryWiseCalories.get(3));
        }

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double calories : workoutCategoryWiseCalories)
            data += calories + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + categoryLabels);
        return barChartUrl.toString();
    }


    public User getLoggedInUser() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        return signedInUser;
    }

    public String getFixedPieChartUrl() {
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=350x350");

        return pieChartUrl.toString();
    }

    public String getFixedBarChartUrl() {
        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=500x500");
        barChartUrl.append("&chxt=y,x");

        return barChartUrl.toString();
    }

    public Date getPreviousDate(Date currentDate) {
        currentDate = new Date(currentDate.getTime() - MILLIS_IN_A_DAY);
        return currentDate;
    }
}
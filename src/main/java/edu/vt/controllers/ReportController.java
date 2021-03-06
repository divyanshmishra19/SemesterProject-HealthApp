/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.*;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;

@Named("reportController")
@SessionScoped
public class ReportController implements Serializable {

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserRecipeConsumedFacade, UserWorkoutDoneFacade and UserFacade bean into the instance variables
    'userRecipeConsumedFacade', 'userWorkoutDoneFacade' and 'userFacade'
    after it is instantiated at runtime.
     */
    @EJB
    private UserRecipeConsumedFacade userRecipeConsumedFacade;

    @EJB
    private UserWorkoutDoneFacade userWorkoutDoneFacade;

    @EJB
    private UserFacade userFacade;

    private StreamedContent file;

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
            pieChartUrl.append(Constants.LABEL + calories.intValue() + "%|" + caloriesLeft.intValue() + "%");
        } else {
            expectedCalories = (expectedCalories / calories) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + expectedCalories.intValue() + "%|" + caloriesOver.intValue() + "%");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getDailyFatsChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeConsumedFacade.getFats(todaysDate, signedInUser.getId());
        List<Integer> fatsPercentage = getPercentFromList(fats);
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Integer fat : fatsPercentage)
            data += fat + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatsPercentage.get(0) + "%|" + fatsPercentage.get(1) + "%|" + fatsPercentage.get(2) + "%|" + fatsPercentage.get(3) + "%");
        return pieChartUrl.toString();
    }

    //Macronutrients Calorie Contribution
    public String getDailyCalorieSplit() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> calorieSplit = userRecipeConsumedFacade.getDailyCalorieSplit(todaysDate, signedInUser.getId());
        List<Integer> caloriePercentage = getPercentFromList(calorieSplit);
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD");
        String data = Constants.DATA;
        for (Integer macro : caloriePercentage)
            data += macro + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + caloriePercentage.get(0) + "%|" + caloriePercentage.get(1) + "%|" + caloriePercentage.get(2) + "%");
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
            pieChartUrl.append(Constants.LABEL + calories.intValue() + "%|" + caloriesLeft.intValue() + "%");
        } else {
            Double expectedCalories = (desiredCaloriesBurned / caloriesBurned) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + expectedCalories.intValue() + "%|" + caloriesOver.intValue() + "%");
        }
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getDailyMicronutrients() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> nutrients = userRecipeConsumedFacade.getMicronutrients(todaysDate, signedInUser.getId());
        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for (Double nutrient : nutrients)
            data += nutrient.intValue() + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + nutrients.get(0).intValue() + "mg|" + nutrients.get(1).intValue() + "mg|" + nutrients.get(2).intValue() + "mg|"
                + nutrients.get(3).intValue() + "mg|" + nutrients.get(4).intValue() + "mg|" + nutrients.get(5).intValue() + "mg");
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getDailyCategoryWiseCalories() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> calorieList = userWorkoutDoneFacade.getCategoryWiseCalories(todaysDate, signedInUser.getId());
        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double calories : calorieList)
            data += calories + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + calorieList.get(0).intValue() + "|" + calorieList.get(1).intValue() +
                "|" + calorieList.get(2).intValue() + "|" + calorieList.get(3).intValue());
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
            pieChartUrl.append(Constants.LABEL + calories.intValue() + "%|" + caloriesLeft.intValue() + "%");
        } else {
            expectedCalories = (expectedCalories / calories) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + expectedCalories.intValue() + "%|" + caloriesOver.intValue() + "%");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getWeeklyFatsChart() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = new ArrayList<>();
        Double sat = 0.0, trans = 0.0, mono = 0.0, poly = 0.0;
        for (int i = 0; i < 7; i++) {
            List<Double> dailyFats = userRecipeConsumedFacade.getFats(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            sat += dailyFats.get(0);
            trans += dailyFats.get(1);
            mono += dailyFats.get(2);
            poly += dailyFats.get(3);
        }
        fats.add(sat);
        fats.add(trans);
        fats.add(mono);
        fats.add(poly);
        List<Integer> fatsPercentage = getPercentFromList(fats);
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Integer fat : fatsPercentage)
            data += fat + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + fatsPercentage.get(0) + "%|" + fatsPercentage.get(1) + "%|" + fatsPercentage.get(2) + "%|" + fatsPercentage.get(3) + "%");
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
            pieChartUrl.append(Constants.LABEL + calories.intValue() + "%|" + caloriesLeft.intValue() + "%");
        } else {
            Double expectedCalories = (desiredCaloriesBurned / caloriesBurned) * 100;
            Double caloriesOver = 100 - expectedCalories;
            pieChartUrl.append(Constants.DATA + expectedCalories + "," + caloriesOver);
            pieChartUrl.append(Constants.LABEL + expectedCalories.intValue() + "%|" + caloriesOver.intValue() + "%");
        }
        return pieChartUrl.toString();
    }

    //Macronutrients Calorie Contribution
    public String getWeeklyCalorieSplit() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> calorieSplit = new ArrayList<>();
        Double carbs = 0.0, proteins = 0.0, fats = 0.0;
        for (int i = 0; i < 7; i++) {
            List<Double> dailyalorieSplit = userRecipeConsumedFacade.getDailyCalorieSplit(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            carbs += dailyalorieSplit.get(0);
            fats += dailyalorieSplit.get(1);
            proteins += dailyalorieSplit.get(2);
        }
        calorieSplit.add(carbs);
        calorieSplit.add(fats);
        calorieSplit.add(proteins);
        List<Integer> caloriePercentage = getPercentFromList(calorieSplit);
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(getFixedPieChartUrl());
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD");
        String data = Constants.DATA;
        for (Integer calorie : caloriePercentage)
            data += calorie + ",";

        pieChartUrl.append(data, 0, data.length() - 1);
        pieChartUrl.append(Constants.LABEL + caloriePercentage.get(0) + "%|" + caloriePercentage.get(1) + "%|" + caloriePercentage.get(2) + "%");
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getWeeklyMicronutrients() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> nutrients = new ArrayList<>();
        Double na = 0.0, ca = 0.0, mg = 0.0, k = 0.0, fe = 0.0, zn = 0.0;
        for (int i = 0; i < 7; i++) {
            List<Double> dailyNutrients = userRecipeConsumedFacade.getMicronutrients(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            na += dailyNutrients.get(0);
            ca += dailyNutrients.get(1);
            k += dailyNutrients.get(2);
            mg += dailyNutrients.get(3);
            fe += dailyNutrients.get(4);
            zn += dailyNutrients.get(5);
        }
        nutrients.add(na);
        nutrients.add(ca);
        nutrients.add(mg);
        nutrients.add(k);
        nutrients.add(fe);
        nutrients.add(zn);
        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for (Double nutrient : nutrients)
            data += nutrient + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + nutrients.get(0).intValue() + "mg|" + nutrients.get(1).intValue() + "mg|" + nutrients.get(2).intValue() +
                "mg|" + nutrients.get(3).intValue() + "mg|" + nutrients.get(4).intValue() + "mg|" + nutrients.get(5).intValue() + "mg");
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getWeeklyCategoryWiseCalories() {
        User signedInUser = getLoggedInUser();
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> workoutCategoryWiseCalories = new ArrayList<>();
        Double calis = 0.0, cardio = 0.0, strength = 0.0, hiit = 0.0;
        for (int i = 0; i < 7; i++) {
            List<Double> dailyWorkoutCategoryWiseCalories = userWorkoutDoneFacade.getCategoryWiseCalories(todaysDate, signedInUser.getId());
            todaysDate = getPreviousDate(todaysDate);

            calis += dailyWorkoutCategoryWiseCalories.get(0);
            cardio += dailyWorkoutCategoryWiseCalories.get(1);
            strength += dailyWorkoutCategoryWiseCalories.get(2);
            hiit += dailyWorkoutCategoryWiseCalories.get(3);
        }
        workoutCategoryWiseCalories.add(calis);
        workoutCategoryWiseCalories.add(cardio);
        workoutCategoryWiseCalories.add(strength);
        workoutCategoryWiseCalories.add(hiit);

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(getFixedBarChartUrl());
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for (Double calories : workoutCategoryWiseCalories)
            data += calories + ",";

        barChartUrl.append(data, 0, data.length() - 1);
        barChartUrl.append(Constants.LABEL + workoutCategoryWiseCalories.get(0).intValue() + "|" + workoutCategoryWiseCalories.get(1).intValue() + "|" +
                workoutCategoryWiseCalories.get(2).intValue() + "|" + workoutCategoryWiseCalories.get(3).intValue());
        return barChartUrl.toString();
    }


    /*
    *************************
    Fetches the LoggedIn User
    *************************
    */
    public User getLoggedInUser() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        return signedInUser;
    }

    /*
    ***********************************
    Fetches the generated PIE CHART URL
    ***********************************
    */
    public String getFixedPieChartUrl() {
        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=350x350");

        return pieChartUrl.toString();
    }

    /*
    ***********************************
    Fetches the generated BAR CHART URL
    ***********************************
    */
    public String getFixedBarChartUrl() {
        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=500x500");
        barChartUrl.append("&chxt=y,x");
        barChartUrl.append(("&chbr=10"));

        return barChartUrl.toString();
    }

    /*
    *******************************
    Fetches the previous day's date
    *******************************
    */
    public Date getPreviousDate(Date currentDate) {
        currentDate = new Date(currentDate.getTime() - Constants.MILLIS_IN_A_DAY);
        return currentDate;
    }

    /*
    ***************************************************************
    Fetches the generated PIE CHART data distribution in percentage
    ***************************************************************
    */
    public List<Integer> getPercentFromList(List<Double> list) {
        List<Integer> ans = new ArrayList<>();
        Double sum = 0.0;
        for (Double num : list)
            sum += num;
        for (Double num : list) {
            num = (num / sum) * 100;
            ans.add(num.intValue());
        }
        return ans;
    }

    /*
    *********************************
    Fetches the file to be downloaded
    *********************************
    */
    public StreamedContent getDownloadFile(int option) throws IOException {
        Methods.preserveMessages();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");

        String url = "";
        switch (option) {
            case 1:
                url = getDailyCalorieChart();
                break;
            case 2:
                url = getDailyWorkout();
                break;
            case 3:
                url = getDailyFatsChart();
                break;
            case 4:
                url = getDailyCalorieSplit();
                break;
            case 5:
                url = getDailyMicronutrients();
                break;
            case 6:
                url = getDailyCategoryWiseCalories();
                break;
            case 7:
                url = getWeeklyCalorieChart();
                break;
            case 8:
                url = getWeeklyWorkout();
                break;
            case 9:
                url = getWeeklyFatsChart();
                break;
            case 10:
                url = getWeeklyCalorieSplit();
                break;
            case 11:
                url = getWeeklyMicronutrients();
                break;
            case 12:
                url = getWeeklyCategoryWiseCalories();
            default:
                Methods.showMessage("Fatal", "Download Failed!",
                        "An unrecognised error has occurred!.");

        }
        try (InputStream in = new URL(url).openStream()) {

            // First we copy the file into the file storage for this application
            Files.copy(in, Paths.get("/opt/wildfly/DocRoot/CS5704-Team10-FileStorage/chartType_" + option + "_" + signedInUser.getUsername() + ".jpg"));

            // The file copied above is read as input stream for user to download charts
            FileInputStream streamOfFileToDownload = new FileInputStream("/opt/wildfly/DocRoot/CS5704-Team10-FileStorage/chartType_" + option + "_" + signedInUser.getUsername() +  ".jpg");

            // File on server is converted to streamed content and is downloaded on clients system
            file = DefaultStreamedContent.builder().contentType("jpg").name("chartType_" + option + "_" + signedInUser.getUsername() + ".jpg").stream(() -> streamOfFileToDownload).build();

            return file;

        } catch (IOException E) {
            Methods.showMessage("Fatal", "Download Failed!",
                    "An unrecognised error has occurred!.");
            E.printStackTrace();
        }
        Methods.showMessage("Information", "Downloaded File!",
                "File has successfully been downloaded");

        return null;
    }
}
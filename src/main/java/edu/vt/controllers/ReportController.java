package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.globals.Constants;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Named("reportController")
@SessionScoped
public class ReportController implements Serializable {

    /*
    The @EJB annotation directs the EJB Container Manager to inject (store) the object reference of the
    UserRecipeFacade and UserWorkoutFacade bean into the instance variables 'userRecipeFacade' and 'userWorkoutFacade'
    after it is instantiated at runtime.
     */
    @EJB
    private UserRecipeFacade userRecipeFacade;

    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    @EJB
    private UserFacade userFacade;

    //Daily Calories Pie Chart
    public String getDailyCalorieChart()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = Double.parseDouble(userRecipeFacade.getTotalDailyCalories(todaysDate.toString(), signedInUser.getId()));
        Double expectedCalories = userFacade.getUserCalorieIntake(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=350x350");
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (calories<expectedCalories)
        {
            calories = (calories/expectedCalories)*100;
            Double caloriesLeft = 100-calories;
            pieChartUrl.append(Constants.DATA+calories+","+caloriesLeft);
            pieChartUrl.append(Constants.LABEL+"Calories Consumed|Calories left");
        }
        else
        {
            expectedCalories = (expectedCalories/calories)*100;
            Double caloriesOver = 100-expectedCalories;
            pieChartUrl.append(Constants.DATA+expectedCalories+","+caloriesOver);
            pieChartUrl.append(Constants.LABEL+"Expected Calories|Calories Over");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getDailyFatsChart()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeFacade.getFats(todaysDate.toString(), signedInUser.getId());
        String fatLabels = "Saturated|Trans|Monounsaturated|Polyunsaturated";

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=350x350");
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for(Double fat: fats)
            data+=fat+",";

        pieChartUrl.append(data, 0, data.length()-1);
        pieChartUrl.append(Constants.LABEL+fatLabels);
        return pieChartUrl.toString();
    }

    //User Workout
    public String getDailyWorkout()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        Double caloriesBurned = userWorkoutFacade.getDailyWorkoutCalories(todaysDate.toString(), signedInUser.getId());
        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=350x350");
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (caloriesBurned<desiredCaloriesBurned)
        {
            Double calories = (caloriesBurned/desiredCaloriesBurned)*100;
            Double caloriesLeft = 100-calories;
            pieChartUrl.append(Constants.DATA+calories+","+caloriesLeft);
            pieChartUrl.append(Constants.LABEL+"Calories Burned|Calories left to Burn");
        }
        else
        {
            Double expectedCalories = (desiredCaloriesBurned/caloriesBurned)*100;
            Double caloriesOver = 100-expectedCalories;
            pieChartUrl.append(Constants.DATA+expectedCalories+","+caloriesOver);
            pieChartUrl.append(Constants.LABEL+"Burnt Calories|Calories Burnt Extra");
        }
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getDailyMicronutrients()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> nutrients = userRecipeFacade.getMicronutrients(todaysDate.toString(), signedInUser.getId());
        String nutrientsLabels = "Sodium|Calcium|Magnesium|Potassium|Iron|Zinc";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=600x600");
        barChartUrl.append("&chxt=y,x");
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for(Double nutrient: nutrients)
            data+=nutrient+",";

        barChartUrl.append(data, 0, data.length()-1);
        barChartUrl.append(Constants.LABEL+nutrientsLabels);
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getDailyCategoryWiseCalories()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> workoutCategoryWiseCalories = userWorkoutFacade.getCategoryWiseCalories(todaysDate.toString(), signedInUser.getId());
        String categoryLabels = "Calisthenics|Cardio|Strength|HIIT";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=600x600");
        barChartUrl.append("&chxt=y,x");
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for(Double calories: workoutCategoryWiseCalories)
            data+=calories+",";

        barChartUrl.append(data, 0, data.length()-1);
        barChartUrl.append(Constants.LABEL+categoryLabels);
        return barChartUrl.toString();
    }

    //Daily Calories Pie Chart
    public String getWeeklyCalorieChart()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = Double.parseDouble(userRecipeFacade.getTotalDailyCalories(todaysDate.toString(), signedInUser.getId()));
        Double expectedCalories = userFacade.getUserCalorieIntake(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=500x500");
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (calories<expectedCalories)
        {
            calories = (calories/expectedCalories)*100;
            Double caloriesLeft = 100-calories;
            pieChartUrl.append(Constants.DATA+calories+","+caloriesLeft);
            pieChartUrl.append(Constants.LABEL+"Calories Consumed|Calories left");
        }
        else
        {
            expectedCalories = (expectedCalories/calories)*100;
            Double caloriesOver = 100-expectedCalories;
            pieChartUrl.append(Constants.DATA+expectedCalories+","+caloriesOver);
            pieChartUrl.append(Constants.LABEL+"Expected Calories|Calories Over");
        }
        return pieChartUrl.toString();
    }

    //Daily Fats Chart
    public String getWeeklyFatsChart()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeFacade.getFats(todaysDate.toString(), signedInUser.getId());
        String fatLabels = "Saturated|Trans|Monounsaturated|Polyunsaturated";

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=500x500");
        pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for(Double fat: fats)
            data+=fat+",";

        pieChartUrl.append(data, 0, data.length()-1);
        pieChartUrl.append(Constants.LABEL+fatLabels);
        return pieChartUrl.toString();
    }

    //User Workout
    public String getWeeklyWorkout()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        Double caloriesBurned = userWorkoutFacade.getDailyWorkoutCalories(todaysDate.toString(), signedInUser.getId());
        Double desiredCaloriesBurned = userFacade.getUserWorkoutCalories(signedInUser.getId());

        StringBuilder pieChartUrl = new StringBuilder();
        pieChartUrl.append(Constants.CHART_API_URL);
        pieChartUrl.append(Constants.PIE_CHART);
        pieChartUrl.append("&chs=500x500");
        pieChartUrl.append("&chco=FFC6A5|FFFF42");
        if (caloriesBurned<desiredCaloriesBurned)
        {
            Double calories = (caloriesBurned/desiredCaloriesBurned)*100;
            Double caloriesLeft = 100-calories;
            pieChartUrl.append(Constants.DATA+calories+","+caloriesLeft);
            pieChartUrl.append(Constants.LABEL+"Calories Burned|Calories left to Burn");
        }
        else
        {
            Double expectedCalories = (desiredCaloriesBurned/caloriesBurned)*100;
            Double caloriesOver = 100-expectedCalories;
            pieChartUrl.append(Constants.DATA+expectedCalories+","+caloriesOver);
            pieChartUrl.append(Constants.LABEL+"Burnt Calories|Calories Burnt Extra");
        }
        return pieChartUrl.toString();
    }

    //Micronutrients Bar Chart
    public String getWeeklyMicronutrients()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> nutrients = userRecipeFacade.getMicronutrients(todaysDate.toString(), signedInUser.getId());
        String nutrientsLabels = "Sodium|Calcium|Magnesium|Potassium|Iron|Zinc";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=500x500");
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for(Double nutrient: nutrients)
            data+=nutrient+",";

        barChartUrl.append(data, 0, data.length()-1);
        barChartUrl.append(Constants.LABEL+nutrientsLabels);
        return barChartUrl.toString();
    }

    //Workout Category wise calories burnt
    public String getWeeklyCategoryWiseCalories()
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = new User();//(User) sessionMap.get("user");
        signedInUser.setId(1);

        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> workoutCategoryWiseCalories = userWorkoutFacade.getCategoryWiseCalories(todaysDate.toString(), signedInUser.getId());
        String categoryLabels = "Calisthenics|Cardio|Strength|HIIT";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=500x500");
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6");
        String data = Constants.DATA;
        for(Double calories: workoutCategoryWiseCalories)
            data+=calories+",";

        barChartUrl.append(data, 0, data.length()-1);
        barChartUrl.append(Constants.LABEL+categoryLabels);
        return barChartUrl.toString();
    }
}
package edu.vt.controllers;

import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.FacadeBeans.UserWorkoutFacade;
import edu.vt.globals.Constants;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

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
        Date todaysDate = new Date(System.currentTimeMillis());
        Double calories = Double.parseDouble(userRecipeFacade.getTotalDailyCalories(todaysDate.toString()));
        Double expectedCalories = Double.parseDouble(userFacade.getUserCalorieIntake());//how to pass userId here

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
    public String getDailyFatsChart()
    {
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeFacade.getFats(todaysDate.toString());
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

    //Micronutrients Bar Chart
    public String getDailyMicronutrients()
    {
        Date todaysDate = new Date(System.currentTimeMillis());
        List<Double> fats = userRecipeFacade.getMicronutrients(todaysDate.toString());
        String fatLabels = "Sodium|Calcium|Magnesium|Potassium|Iron|Zinc";

        StringBuilder barChartUrl = new StringBuilder();
        barChartUrl.append(Constants.CHART_API_URL);
        barChartUrl.append(Constants.BAR_CHART);
        barChartUrl.append("&chs=500x500");
        barChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE|003AE1");
        String data = Constants.DATA;
        for(Double fat: fats)
            data+=fat+",";

        barChartUrl.append(data, 0, data.length()-1);
        barChartUrl.append(Constants.LABEL+fatLabels);
        return barChartUrl.toString();
    }

}

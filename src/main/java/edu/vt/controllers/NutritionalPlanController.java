package edu.vt.controllers;

import edu.vt.EntityBeans.*;
import edu.vt.FacadeBeans.*;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named("nutritionalController")
@SessionScoped
public class NutritionalPlanController implements Serializable {

    private List<NutritionalPlan> listOfNutritionalPlans;
    private NutritionalPlan selected;

    @EJB
    private RecipeFacade recipeFacade;

    @EJB
    private WorkoutFacade workoutFacade;

    @EJB
    private UserRecipeFacade userRecipeFacade;

    @EJB
    private UserWorkoutFacade userWorkoutFacade;

    @EJB
    private UserFacade userFacade;

    @EJB
    private NutritionalPlanFacade nutritionalPlanFacade;

    public List<NutritionalPlan> getListOfNutritionalPlans() {
        if (listOfNutritionalPlans == null) {
            listOfNutritionalPlans = nutritionalPlanFacade.findAll();
        }
        return listOfNutritionalPlans;
    }

    public void setListOfNutritionalPlans(List<NutritionalPlan> listOfNutritionalPlans) {
        this.listOfNutritionalPlans = listOfNutritionalPlans;
    }

    public NutritionalPlan getSelected() {
        return selected;
    }

    public void setSelected(NutritionalPlan selected) {
        this.selected = selected;
    }

    public RecipeFacade getRecipeFacade() {
        return recipeFacade;
    }

    public void setRecipeFacade(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    public WorkoutFacade getWorkoutFacade() {
        return workoutFacade;
    }

    public void setWorkoutFacade(WorkoutFacade workoutFacade) {
        this.workoutFacade = workoutFacade;
    }

    public UserRecipeFacade getUserRecipeFacade() {
        return userRecipeFacade;
    }

    public void setUserRecipeFacade(UserRecipeFacade userRecipeFacade) {
        this.userRecipeFacade = userRecipeFacade;
    }

    public UserWorkoutFacade getUserWorkoutFacade() {
        return userWorkoutFacade;
    }

    public void setUserWorkoutFacade(UserWorkoutFacade userWorkoutFacade) {
        this.userWorkoutFacade = userWorkoutFacade;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public NutritionalPlanFacade getNutritionalPlanFacade() {
        return nutritionalPlanFacade;
    }

    public void setNutritionalPlanFacade(NutritionalPlanFacade nutritionalPlanFacade) {
        this.nutritionalPlanFacade = nutritionalPlanFacade;
    }

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected nutritionalPlan object if any
        selected = null;
        return "/NutritionalPlan/PlanList?faces-redirect=true";
    }

    public void optIn() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User editUser = (User) sessionMap.get("user");

        if(editUser==null)
        {
            Methods.preserveMessages();
            Methods.showMessage("Error", "Log-In Required!",
                    "You need to log-in to an existing account or create a new one to access this feature!");
            return;
        }

        //getting calorie goals from selected plan and updating user goals.
        double calorieIntakeGoal = selected.getDailyCalorieIntake();
        double calorieBurnGoal = selected.getDailyCalorieBurn();
        editUser.setDailyCalorieBurn(calorieBurnGoal);
        editUser.setDailyCalorieIntake(calorieIntakeGoal);
        userFacade.edit(editUser);

        //adding recipes from nutritional plan to user's custom list
        String recipeIds[] = selected.getRecipeIds().split(",");
        String recipeNames[] = selected.getRecipeNames().split(",");
        for (int i = 0; i < recipeIds.length; i++) {
            //get recipe from public list
            Recipe recipe = recipeFacade.findRecipeById(Integer.parseInt(recipeIds[i].trim()));

            //check if recipe of same name exists in user's custom recipe list or not
            List<UserRecipe> userRecipeList = userRecipeFacade.findUserRecipesByName(editUser.getId(), recipeNames[i]);

            //if any recipe with this name already exists, we do not add it to the custom list again
            if (userRecipeList.size()!=0)
                continue;
            else {
                UserRecipe userRecipe = new UserRecipe();
                //no recipe with this name exists in user list, so we add it
                userRecipe.setName(recipe.getName());

                //setting total calories of the dish
                userRecipe.setCalories(recipe.getCalories());

                //setting fat split-up
                userRecipe.setFatTotal(recipe.getFatTotal());
                userRecipe.setFatTrans(recipe.getFatTrans());
                userRecipe.setFatPoly(recipe.getFatPoly());
                userRecipe.setFatMono(recipe.getFatMono());
                userRecipe.setFatSat(recipe.getFatSat());

                //setting macronutrients
                userRecipe.setCarbs(recipe.getCarbs());
                userRecipe.setProtein(recipe.getProtein());

                //setting macronutrients calorie contribution
                userRecipe.setCarbCal(recipe.getCarbCal());
                userRecipe.setProteinCal(recipe.getProteinCal());
                userRecipe.setFatCal(recipe.getFatCal());

                //setting micronutrients and minerals
                userRecipe.setSodium(recipe.getSodium());
                userRecipe.setCalcium(recipe.getCalcium());
                userRecipe.setPotassium(recipe.getPotassium());
                userRecipe.setMagnesium(recipe.getMagnesium());
                userRecipe.setIron(recipe.getIron());
                userRecipe.setZinc(recipe.getZinc());

                //setting ingredients and diet labels
                userRecipe.setDietLabels(recipe.getDietLabels());
                userRecipe.setIngredients(recipe.getIngredients());

                userRecipe.setUserId(editUser);
                userRecipeFacade.edit(userRecipe);
            }
        }

        //adding workouts from nutritional plan to user's custom list
        String workoutIds[] = selected.getWorkoutIds().split(",");
        String workoutNames[] = selected.getWorkoutNames().split(",");
        for (int i = 0; i < workoutIds.length; i++) {
            //get workout from public list
            Workout workout = workoutFacade.findWorkoutById(Integer.parseInt(workoutIds[i].trim()));

            //check if workout of same name exists in user's custom workout list or not
            List<UserWorkout> userWorkoutList = userWorkoutFacade.findUserWorkoutsByName(editUser.getId(), workoutNames[i]);

            //if any workout with this name already exists, we do not add it to the custom list again
            if (userWorkoutList.size()!=0)
                continue;
            else {
                UserWorkout userWorkout = new UserWorkout();
                //no workout with this name exists in user list, so we add it
                userWorkout.setYoutubeTutorialVideoId(workout.getYoutubeTutorialVideoId());
                userWorkout.setBurnRate(workout.getBurnRate());
                userWorkout.setCategory(workout.getCategory());
                userWorkout.setName(workout.getName());
                userWorkout.setUserId(editUser);

                userWorkoutFacade.edit(userWorkout);
            }
        }

        //we have edited user goals and added new recipes and workouts to his custom lists,
        // thus opting him in the nutritional plan.
        Methods.preserveMessages();
        Methods.showMessage("Information", "Opted In!",
                "You have successfully opted in this nutritional plan");

        selected = null;
    }

}

package edu.vt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserRecipeConsumed;
import edu.vt.FacadeBeans.UserRecipeConsumedFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import edu.vt.payloads.RecipePayload;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("userRecipeController")
@RequestScoped
public class UserRecipeController implements Serializable {

    private List<UserRecipe> listOfUserRecipes;
    private UserRecipe selected;
    private UserRecipeConsumed recipeConsumed;
    private RecipePayload recipePayload;
    private String recipeName;
    private String ingredients;


    @EJB
    UserRecipeFacade userRecipeFacade;

    @EJB
    UserRecipeConsumedFacade userRecipeConsumedFacade;

    public List<UserRecipe> getListOfUserRecipes() {

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        User signedInUser = (User) sessionMap.get("user");
        return userRecipeFacade.findRecipesByUserId(signedInUser.getId());
    }

    public void setListOfUserRecipes(List<UserRecipe> listOfUserRecipes) {
        this.listOfUserRecipes = listOfUserRecipes;
    }

    public UserRecipe getSelected() {
        return selected;
    }

    public void setSelected(UserRecipe selected) {
        this.selected = selected;
    }

    public UserRecipeFacade getUserRecipeFacade() {
        return userRecipeFacade;
    }

    public void setUserRecipeFacade(UserRecipeFacade userRecipeFacade) {
        this.userRecipeFacade = userRecipeFacade;
    }

    public UserRecipeConsumed getRecipeConsumed() {
        return recipeConsumed;
    }

    public void setRecipeConsumed(UserRecipeConsumed recipeConsumed) {
        this.recipeConsumed = recipeConsumed;
    }

    public UserRecipeConsumedFacade getUserRecipeConsumedFacade() {
        return userRecipeConsumedFacade;
    }

    public void setUserRecipeConsumedFacade(UserRecipeConsumedFacade userRecipeConsumedFacade) {
        this.userRecipeConsumedFacade = userRecipeConsumedFacade;
    }

    public RecipePayload getRecipePayload() {
        return recipePayload;
    }

    public void setRecipePayload(RecipePayload recipePayload) {
        this.recipePayload = recipePayload;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected userRecipe object if any
        selected = null;
        return "/health/List?faces-redirect=true";
    }


    public void prepareCreate() {
        /*
        Instantiate a new Video object and store its object reference into
        instance variable 'selected'. The UserRecipe class is defined in UserRecipe.java
         */
        selected = new UserRecipe();
    }


    public void create() throws IOException {
        Methods.preserveMessages();
        recipePayload = new RecipePayload(recipeName, ingredients);
        ObjectMapper Obj = new ObjectMapper();
        try {
            String jsonStr = Obj.writeValueAsString(recipePayload);

            String TARGET = "https://api.edamam.com/api/nutrition-details?app_id=" + Constants.API_ID + "&app_key=" + Constants.API_KEY;
            URI uri = new URI(TARGET);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonStr))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String responseString = response.body();

            Map<String, Map<String, Map<String, Object>>> result = new ObjectMapper().readValue(responseString, HashMap.class);
            Map<String, Map<String, Object>> totalNutrients = result.get("totalNutrients");
            Map<String, Map<String, Object>> totalNutrientsKCal = result.get("totalNutrientsKCal");
            List<String> dietLabels = (List<String>) result.get("dietLabels");

            //basic user entered details - name and ingredients
            selected.setName(recipeName);
            selected.setIngredients(ingredients);

            //most important field - Calories
            selected.setCalories((Double) totalNutrients.get("ENERC_KCAL").get("quantity"));

            //fat and fat distribution
            selected.setFatTotal((Double) totalNutrients.get("FAT").get("quantity"));
            selected.setFatSat((Double) totalNutrients.get("FASAT").get("quantity"));
            selected.setFatMono((Double) totalNutrients.get("FAMS").get("quantity"));
            selected.setFatTrans((Double) totalNutrients.get("FATRN").get("quantity"));
            selected.setFatPoly((Double) totalNutrients.get("FAPU").get("quantity"));

            //Macronutrients - Carbs, Proteins...
            selected.setCarbs((Double) totalNutrients.get("CHOCDF").get("quantity"));
            selected.setProtein((Double) totalNutrients.get("PROCNT").get("quantity"));

            //Contribution of macronutrients to daily requirement...
            selected.setProteinCal((Double) totalNutrientsKCal.get("PROCNT_KCAL").get("quantity"));
            selected.setCarbCal((Double) totalNutrientsKCal.get("CHOCDF_KCAL").get("quantity"));
            selected.setFatCal((Double) totalNutrientsKCal.get("FAT_KCAL").get("quantity"));

            //Micronutrients - Minerals
            selected.setSodium((Double) totalNutrients.get("NA").get("quantity"));
            selected.setPotassium((Double) totalNutrients.get("K").get("quantity"));
            selected.setCalcium((Double) totalNutrients.get("CA").get("quantity"));
            selected.setMagnesium((Double) totalNutrients.get("MG").get("quantity"));
            selected.setIron((Double) totalNutrients.get("FE").get("quantity"));
            selected.setZinc((Double) totalNutrients.get("ZN").get("quantity"));

            //Diet Labels
            StringBuilder dietLabel = new StringBuilder();
            if (dietLabels != null) {
                for (String labels : dietLabels)
                    dietLabel.append(labels + ",");
            } else
                dietLabel.append("NA");
            selected.setDietLabels(dietLabel.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Methods.showMessage("Fatal", "Application Failed!",
                    "An unrecognised error has occurred!.");
        }

        persist(JsfUtil.PersistAction.CREATE, "Public Video was successfully created.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserRecipes = null;    // Invalidate listOfMovies to trigger re-query.
        }
    }

    public void destroy() {
        Methods.preserveMessages();

        persist(JsfUtil.PersistAction.DELETE, "User Recipe was successfully deleted.");

        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            selected = null;        // Remove selection
            listOfUserRecipes = null;    // Invalidate listOfMovies to trigger re-query.
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
                    userRecipeFacade.edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove(selected) method performs the DELETE operation of the "selected"
                     object in the database.

                     PublicVideoFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    userRecipeFacade.remove(selected);
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
        recipeConsumed = new UserRecipeConsumed();

        recipeConsumed.setRecipeId(selected);
        recipeConsumed.setDate(todaysDate);

        Methods.preserveMessages();
        userRecipeConsumedFacade.edit(recipeConsumed);
        Methods.showMessage("Information", "Progress Updated!",
                "Meal was successfully added to your daily progress!.");
    }
}

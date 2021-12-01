package edu.vt.controllers;

import edu.vt.EntityBeans.Recipe;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserRecipe;
import edu.vt.FacadeBeans.RecipeFacade;
import edu.vt.FacadeBeans.UserRecipeFacade;
import edu.vt.globals.Methods;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import edu.vt.globals.Constants;
import edu.vt.payloads.RecipePayload;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;

@Named("recipeController")
@RequestScoped

public class RecipeController implements Serializable {

    private List<Recipe> listOfRecipes;
    private Recipe selected;
    private UserRecipe userRecipe;

    @EJB
    private RecipeFacade recipeFacade;

    @EJB
    private UserRecipeFacade userRecipeFacade;


    public Recipe getSelected() {
        return selected;
    }

    public void setSelected(Recipe selected) {
        this.selected = selected;
    }

    public List<Recipe> getListOfRecipes() {
        if (listOfRecipes == null) {
            listOfRecipes = recipeFacade.findAll();
        }
        return listOfRecipes;
    }

    public void setListOfRecipes(List<Recipe> listOfRecipes) {
        this.listOfRecipes = listOfRecipes;
    }

    public UserRecipe getUserRecipe() {
        return userRecipe;
    }

    public void setUserRecipe(UserRecipe userRecipe) {
        this.userRecipe = userRecipe;
    }

    public RecipeFacade getRecipeFacade() {
        return recipeFacade;
    }

    public void setRecipeFacade(RecipeFacade recipeFacade) {
        this.recipeFacade = recipeFacade;
    }

    public UserRecipeFacade getUserRecipeFacade() {
        return userRecipeFacade;
    }

    public void setUserRecipeFacade(UserRecipeFacade userRecipeFacade) {
        this.userRecipeFacade = userRecipeFacade;
    }

    public void unselect() {
        selected = null;
    }

    public String cancel() {
        // Unselect previously selected movie object if any
        selected = null;
        return "/recipe/List?faces-redirect=true";
    }

    public void share() {
        userRecipe = new UserRecipe();
        User signedIn = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
        if(signedIn==null)
        {
            Methods.showMessage("Information", "Unable to Share!", "To share a recipe, user must be logged in");
            return;
        }

        userRecipe.setCalcium(selected.getCalcium());
        userRecipe.setCalories(selected.getCalories());
        userRecipe.setCarbCal(selected.getCarbCal());
        userRecipe.setCarbs(selected.getCarbs());
        userRecipe.setDietLabels(selected.getDietLabels());
        userRecipe.setFatCal(selected.getFatCal());
        //and all the rest of the fields..........
        userRecipe.setUserId(signedIn);

        Methods.preserveMessages();
        userRecipeFacade.edit(userRecipe);
        Methods.showMessage("Information", "Shared!", "Public recipe was successfully shared.");
    }

    public void parseJSONResponse(){

        String apiURL = Constants.API_URL + "?app_id=" + Constants.API_ID + "&app_key=" + Constants.API_KEY;


        RecipePayload recipePayload = new RecipePayload(this.selected.getName(), this.selected.getIngredients());
        ObjectMapper Obj = new ObjectMapper();
        String finalResponse = "No response";

        try {
            String jsonStr = Obj.writeValueAsString(recipePayload);
            System.out.println(jsonStr);
            finalResponse = Methods.readUrlContent(apiURL, jsonStr);
            JSONObject searchResultsJsonObject = new JSONObject(finalResponse);


            this.selected.setCalories(searchResultsJsonObject.optInt("calories"));

            JSONObject totalNutrients = searchResultsJsonObject.getJSONObject("totalNutrients");

            JSONObject proteinJSON = totalNutrients.getJSONObject("PROCNT");
            this.selected.setProtein(proteinJSON.optFloat("quantity"));

            JSONObject carbJSON = totalNutrients.getJSONObject("CHOCDF");
            this.selected.setCarbs(carbJSON.optFloat("quantity"));

            JSONObject fatJSON = totalNutrients.getJSONObject("FAT");
            this.selected.setFatTotal(fatJSON.optFloat("quantity"));
            fatJSON = totalNutrients.getJSONObject("FASAT");
            this.selected.setFatSat(fatJSON.optFloat("quantity"));
            fatJSON = totalNutrients.getJSONObject("FAMS");
            this.selected.setFatMono(fatJSON.optFloat("quantity"));
            fatJSON = totalNutrients.getJSONObject("FAPU");
            this.selected.setFatPoly(fatJSON.optFloat("quantity"));



            JSONObject sodiumJSON = totalNutrients.getJSONObject("NA");
            this.selected.setSodium(sodiumJSON.optFloat("quantity"));
            JSONObject calciumJSON = totalNutrients.getJSONObject("CA");
            this.selected.setCalcium(calciumJSON.optFloat("quantity"));
            JSONObject magnesiumJSON = totalNutrients.getJSONObject("MG");
            this.selected.setMagnesium(magnesiumJSON.optFloat("quantity"));
            JSONObject potassiumJSON = totalNutrients.getJSONObject("K");
            this.selected.setPotassium(potassiumJSON.optFloat("quantity"));
            JSONObject ironJSON = totalNutrients.getJSONObject("FE");
            this.selected.setIron(ironJSON.optFloat("quantity"));
            JSONObject zincJSON = totalNutrients.getJSONObject("ZN");
            this.selected.setZinc(zincJSON.optFloat("quantity"));


            JSONObject totalNutrientsKCal = searchResultsJsonObject.getJSONObject("totalNutrientsKCal");

            JSONObject proteinCalJSON = totalNutrientsKCal.getJSONObject("PROCNT_KCAL");
            this.selected.setProteinCal(proteinCalJSON.optInt("quantity"));

            JSONObject carbCalJSON = totalNutrientsKCal.getJSONObject("CHOCDF_KCAL");
            this.selected.setCarbCal(carbCalJSON.optInt("quantity"));

            JSONObject fatCalJSON = totalNutrientsKCal.getJSONObject("FAT_KCAL");
            this.selected.setFatCal(fatCalJSON.optInt("quantity"));
             /*
             ==================
             Recipe Diet Labels
             ==================
             */
            JSONArray dietLabelsAsArray = searchResultsJsonObject.getJSONArray("dietLabels");

            String dietLabels = "";
            int dietLabelsArrayLength = dietLabelsAsArray.length();

            if (dietLabelsArrayLength > 0) {
                for (int j = 0; j < dietLabelsArrayLength; j++) {
                    String aDietLabel = dietLabelsAsArray.optString(j, "");
                    if (j < dietLabelsArrayLength - 1) {
                        aDietLabel = aDietLabel + ", ";
                    }
                    dietLabels = dietLabels + aDietLabel;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

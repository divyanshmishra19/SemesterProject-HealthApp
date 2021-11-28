package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserRecipeFacade extends AbstractFacade<UserRecipe>{
    public UserRecipeFacade(Class<UserRecipe> entityClass) {
        super(entityClass);
    }

    public UserRecipeFacade() {
        super(UserRecipe.class);
    }

    public String getTotalDailyCalories(String toString, int userId) {
        return "1405";
    }

    public List<Double> getFats(String toString, int userId) {
        List<Double> fatList = new ArrayList<>();
        fatList.add(23.45);
        fatList.add(42.15);
        fatList.add(18.58);
        fatList.add(33.17);

        return fatList;
    }

    public List<Double> getMicronutrients(String toString, int userId) {
        List<Double> micronutrientsList = new ArrayList<>();
        micronutrientsList.add(23.45);
        micronutrientsList.add(42.15);
        micronutrientsList.add(18.58);
        micronutrientsList.add(33.17);
        micronutrientsList.add(16.39);
        micronutrientsList.add(53.12);

        return micronutrientsList;
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
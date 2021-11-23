package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Recipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserRecipeFacade extends AbstractFacade<Recipe>{
    public UserRecipeFacade(Class<Recipe> entityClass) {
        super(entityClass);
    }

    public UserRecipeFacade() {
        super(Recipe.class);
    }

    public String getTotalDailyCalories(String toString) {
        return "1405";
    }

    public List<Double> getFats(String toString) {
        List<Double> fatList = new ArrayList<>();
        fatList.add(23.45);
        fatList.add(42.15);
        fatList.add(18.58);
        fatList.add(33.17);

        return fatList;
    }

    public List<Double> getMicronutrients(String toString) {
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

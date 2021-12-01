package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserRecipeConsumed;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserRecipeConsumedFacade extends AbstractFacade<UserRecipeConsumed> {

    @PersistenceContext(unitName = "SemesterProject-HealthAppPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public UserRecipeConsumedFacade(Class<UserRecipeConsumed> entityClass) {
        super(entityClass);
    }

    public UserRecipeConsumedFacade() {
        super(UserRecipeConsumed.class);
    }

    public Double getTotalDailyCalories(Date date, int userId) {
        return 1405.0;
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
}

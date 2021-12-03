package edu.vt.FacadeBeans;


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
        Double ans = (Double) getEntityManager().createQuery(
                        "SELECT SUM(d.calories) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date).getSingleResult();

        return ans;
    }

    public List<Double> getFats(Date date, int userId) {
        Double mono = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.fatMono) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double poly = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.fatPoly) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double trans = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.fatTrans) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double sats = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.fatSat) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        List<Double> fatList = new ArrayList<>();
        fatList.add(sats);
        fatList.add(trans);
        fatList.add(mono);
        fatList.add(poly);

        return fatList;
    }

    public List<Double> getMicronutrients(Date date, int userId) {
        Double na = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.sodium) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double ca = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.calcium) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double mg = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.magnesium) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double k = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.potassium) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double fe = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.iron) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double zn = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.zinc) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        List<Double> micronutrientsList = new ArrayList<>();
        micronutrientsList.add(na);
        micronutrientsList.add(ca);
        micronutrientsList.add(mg);
        micronutrientsList.add(k);
        micronutrientsList.add(fe);
        micronutrientsList.add(zn);

        return micronutrientsList;
    }

    public List<Double> getDailyCalorieSplit(Date date, Integer userId) {
        Double carbsCal = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.carbCal) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double fatsCal = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.fatCal) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        Double proteinCal = (Double)getEntityManager().createQuery(
                        "SELECT SUM(d.proteinCal) FROM UserRecipeConsumed c " +
                                "JOIN UserRecipe d on c.recipeId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();

        List<Double> calList = new ArrayList<>();
        calList.add(carbsCal);
        calList.add(fatsCal);
        calList.add(proteinCal);

        return calList;
    }
}

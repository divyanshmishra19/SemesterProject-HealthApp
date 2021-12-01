package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipeConsumed;
import edu.vt.EntityBeans.UserWorkoutDone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserWorkoutDoneFacade extends AbstractFacade<UserWorkoutDone> {

    @PersistenceContext(unitName = "SemesterProject-HealthAppPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public UserWorkoutDoneFacade(Class<UserWorkoutDone> entityClass) {
        super(entityClass);
    }

    public UserWorkoutDoneFacade() {
        super(UserWorkoutDone.class);
    }

    public List<Double> getCategoryWiseCalories(Date date, Integer userId) {
        String category[] = {"Calisthenics", "Cardio", "Strength", "HIIT"};
        Double calis = (Double) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c JOIN UserWorkout d " +
                                "Where d.userId = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[0])
                .getSingleResult();

        Double cardio = (Double) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c JOIN UserWorkout d " +
                                "Where d.userId = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[1])
                .getSingleResult();

        Double strength = (Double) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c JOIN UserWorkout d " +
                                "Where d.userId = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[2])
                .getSingleResult();

        Double hiit = (Double) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c JOIN UserWorkout d " +
                                "Where d.userId = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[3])
                .getSingleResult();

        List<Double> categoryWiseCalories = new ArrayList<>();

        categoryWiseCalories.add(calis);
        categoryWiseCalories.add(cardio);
        categoryWiseCalories.add(strength);
        categoryWiseCalories.add(hiit);

        return categoryWiseCalories;
    }

    public Double getDailyWorkoutCalories(Date date, Integer userId) {
        return (Double) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c JOIN UserWorkout d " +
                                "Where d.userId = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();
    }
}

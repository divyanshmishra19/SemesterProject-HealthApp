/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserWorkoutDone;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserWorkoutDoneFacade extends AbstractFacade<UserWorkoutDone> {

    @PersistenceContext(unitName = "HealthTechPU")
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
        Long calis = (Long) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c " +
                                "JOIN UserWorkout d on c.workoutId = d " +
                                "Where d.userId.id = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[0])
                .getSingleResult();

        Long cardio = (Long) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c " +
                                "JOIN UserWorkout d on c.workoutId = d " +
                                "Where d.userId.id = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[1])
                .getSingleResult();

        Long strength = (Long) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c " +
                                "JOIN UserWorkout d on c.workoutId = d " +
                                "Where d.userId.id = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[2])
                .getSingleResult();

        Long hiit = (Long) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c " +
                                "JOIN UserWorkout d on c.workoutId = d " +
                                "Where d.userId.id = :userId AND c.date = :date AND d.category LIKE :category")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("category", category[3])
                .getSingleResult();

        List<Double> categoryWiseCalories = new ArrayList<>();

        categoryWiseCalories.add(calis != null ? calis.doubleValue() : 0.0);
        categoryWiseCalories.add(cardio != null ? cardio.doubleValue() : 0.0);
        categoryWiseCalories.add(strength != null ? strength.doubleValue() : 0.0);
        categoryWiseCalories.add(hiit != null ? hiit.doubleValue() : 0.0);

        return categoryWiseCalories;
    }

    public Integer getDailyWorkoutCalories(Date date, Integer userId) {
        Long ans = (Long) getEntityManager().createQuery(
                        "SELECT SUM(c.calories) FROM UserWorkoutDone c " +
                                "JOIN UserWorkout d on c.workoutId = d " +
                                "Where d.userId.id = :userId AND c.date = :date")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .getSingleResult();
        if (ans == null)
            return 0;
        return ans.intValue();
    }

}

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

    public List<Double> getCategoryWiseCalories(String toString, Integer id) {
        List<Double> categoryWiseCalories = new ArrayList<>();

        categoryWiseCalories.add(232.0);
        categoryWiseCalories.add(146.7);
        categoryWiseCalories.add(69.4);
        categoryWiseCalories.add(762.2);

        return categoryWiseCalories;
    }

    public Double getDailyWorkoutCalories(Date date, Integer id) {
        return 1000.0;
    }
}

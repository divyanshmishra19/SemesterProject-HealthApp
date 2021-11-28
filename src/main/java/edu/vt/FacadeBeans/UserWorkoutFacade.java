package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserWorkout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserWorkoutFacade extends AbstractFacade<UserWorkout>{
    public UserWorkoutFacade(Class<UserWorkout> entityClass) {
        super(entityClass);
    }

    public UserWorkoutFacade() {
        super(UserWorkout.class);
    }


    public List<Double> getCategoryWiseCalories(String toString, Integer id) {
        List<Double> categoryWiseCalories = new ArrayList<>();

        categoryWiseCalories.add(232.0);
        categoryWiseCalories.add(146.7);
        categoryWiseCalories.add(69.4);
        categoryWiseCalories.add(762.2);

        return categoryWiseCalories;
    }

    public Double getDailyWorkoutCalories(String toString, Integer id) {
        return 1000.0;
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}

package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserWorkout;

import javax.persistence.EntityManager;

public class UserWorkoutFacade extends AbstractFacade<UserWorkout>{
    public UserWorkoutFacade(Class<UserWorkout> entityClass) {
        super(entityClass);
    }

    public UserWorkoutFacade() {
        super(UserWorkout.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}

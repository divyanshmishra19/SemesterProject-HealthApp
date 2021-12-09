/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;
import edu.vt.EntityBeans.UserWorkout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserWorkoutFacade extends AbstractFacade<UserWorkout> {
    @PersistenceContext(unitName = "HealthTechPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public UserWorkoutFacade(Class<UserWorkout> entityClass) {
        super(entityClass);
    }

    public UserWorkoutFacade() {
        super(UserWorkout.class);
    }

    /*
    ================
    Instance Methods
    ================
    */

    //returns list of user workouts by user id
    public List<UserWorkout> findWorkoutsByUserId(int id) {
        return (List<UserWorkout>) getEntityManager().createQuery(
                        "Select c From UserWorkout c Where c.userId.id = :userId")
                .setParameter("userId", id)
                .getResultList();
    }

    //returns list of user workouts with matching name for a particular user
    public List<UserWorkout> findUserWorkoutsByName(int id, String name) {
        return (List<UserWorkout>) getEntityManager().createQuery(
                        "Select c From UserWorkout c Where c.userId.id = :userId AND c.name LIKE :name")
                .setParameter("userId", id)
                .setParameter("name", name)
                .getResultList();
    }
}
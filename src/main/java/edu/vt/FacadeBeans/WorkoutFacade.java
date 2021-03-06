/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Workout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class WorkoutFacade extends AbstractFacade<Workout> {

    /*
    ---------------------------------------------------------------------------------------------
    The EntityManager is an API that enables database CRUD (Create Read Update Delete) operations
    and complex database searches. An EntityManager instance is created to manage entities
    that are defined by a persistence unit. The @PersistenceContext annotation below associates
    the entityManager instance with the persistence unitName identified below.
    ---------------------------------------------------------------------------------------------
     */
    @PersistenceContext(unitName = "HealthTechPU")
    private EntityManager entityManager;

    // Obtain the object reference of the EntityManager instance in charge of
    // managing the entities in the persistence context identified above.
    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /*
    This constructor method invokes its parent AbstractFacade's constructor method,
    which in turn initializes its entity class type T and entityClass instance variable.
     */
    public WorkoutFacade() {
        super(Workout.class);
    }

    //return workout by its id
    public Workout findWorkoutById(int id) {
        return (Workout) getEntityManager().createQuery(
                        "Select c From Workout c Where c.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }
}

package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserWorkout;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserWorkoutFacade extends AbstractFacade<UserWorkout> {
    @PersistenceContext(unitName = "SemesterProject-HealthAppPU")
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

    public List<UserWorkout> findWorkoutsByUserId(int id) {
        return (List<UserWorkout>) getEntityManager().createQuery(
                        "Select c From UserWorkout c Where c.userId.id = :userId")
                .setParameter("userId", id)
                .getResultList();
    }
}
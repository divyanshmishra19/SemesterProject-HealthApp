package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserRecipeFacade extends AbstractFacade<UserRecipe> {

    @PersistenceContext(unitName = "SemesterProject-HealthAppPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public UserRecipeFacade(Class<UserRecipe> entityClass) {
        super(entityClass);
    }

    public UserRecipeFacade() {
        super(UserRecipe.class);
    }

    /*
    ================
    Instance Methods
    ================
    */

    public List<UserRecipe> findRecipesByUserId(int id) {
        return (List<UserRecipe>) getEntityManager().createQuery(
                        "Select c From UserRecipe c Where c.userId.id = :userId")
                .setParameter("userId", id)
                .getResultList();
    }
}
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Recipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RecipeFacade extends AbstractFacade<Recipe> {

    @PersistenceContext(unitName = "SemesterProject-HealthAppPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /*
    This constructor method invokes its parent AbstractFacade's constructor method,
    which in turn initializes its entity class type T and entityClass instance variable.
     */
    public RecipeFacade() {
        super(Recipe.class);
    }

}

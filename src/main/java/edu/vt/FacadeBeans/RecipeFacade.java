/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.Recipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class RecipeFacade extends AbstractFacade<Recipe> {

    @PersistenceContext(unitName = "HealthTechPU")
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

    //find recipe by its id
    public Recipe findRecipeById(int id) {
        return (Recipe) getEntityManager().createQuery(
                        "Select c From Recipe c Where c.id = :id")
                .setParameter("id", id)
                .getSingleResult();
    }

}

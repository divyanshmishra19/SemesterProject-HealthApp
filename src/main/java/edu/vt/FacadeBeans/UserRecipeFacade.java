/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserRecipe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// @Stateless annotation implies that the conversational state with the client shall NOT be maintained.
@Stateless
public class UserRecipeFacade extends AbstractFacade<UserRecipe> {

    @PersistenceContext(unitName = "HealthTechPU")
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

    //returns user recipes by user id
    public List<UserRecipe> findRecipesByUserId(int id) {
        return (List<UserRecipe>) getEntityManager().createQuery(
                        "Select c From UserRecipe c Where c.userId.id = :userId")
                .setParameter("userId", id)
                .getResultList();
    }

    //finds recipes by recipe name and user id
    public List<UserRecipe> findUserRecipesByName(int id, String name) {
        return (List<UserRecipe>) getEntityManager().createQuery(
                        "Select c From UserRecipe c Where c.userId.id = :userId AND c.name LIKE :name")
                .setParameter("userId", id)
                .setParameter("name", name)
                .getResultList();
    }
}
/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/*
The @Entity annotation designates this class as a JPA Entity POJO class
representing the UserRecipeConsumed table in the HealthTechDB database.
 */
@Entity
// Name of the database table represented
@Table(name = "UserRecipeConsumed")
public class UserRecipeConsumed {
    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the UserRecipeConsumed table in the HealthTechDB database.

    CREATE TABLE UserRecipeConsumed
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_recipe_id INT UNSIGNED,
    date DATE NOT NULL,
    FOREIGN KEY (user_recipe_id) REFERENCES UserRecipe(id) ON DELETE CASCADE
);

    ========================================================
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    //UserRecipe-ID -> Foreign Key
    @JoinColumn(name = "user_recipe_id", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.ALL})
    private UserRecipe recipeId;

    @NotNull
    @Column(name = "date")
    @Basic(optional = false)
    private Date date;

    /*
    =============================================================================
    Class constructors for instantiating a UserRecipeConsumed entity object to
    represent a row in the UserRecipeConsumed table in the HealthTechDB database.
    =============================================================================
     */
    public UserRecipeConsumed() {
    }

    public UserRecipeConsumed(Integer id) {
        this.id = id;
    }

    public UserRecipeConsumed(Integer id, UserRecipe recipeId, Date date) {
        this.id = id;
        this.recipeId = recipeId;
        this.date = date;
    }

    /*
    ==============================================================
    Getter and Setter methods for the attributes (columns)
    of the UserRecipeConsumed table in the HealthTechDB database.
    ===============================================================
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRecipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UserRecipe recipeId) {
        this.recipeId = recipeId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the UserRecipeConsumed object identified by 'object' is the same as the UserRecipeConsumed object identified by 'id'
     Parameter object = UserRecipeConsumed object identified by 'object'
     Returns True if the UserRecipeConsumed 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserRecipeConsumed)) {
            return false;
        }
        UserRecipeConsumed other = (UserRecipeConsumed) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}

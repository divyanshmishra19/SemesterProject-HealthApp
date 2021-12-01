package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
// Name of the database table represented
@Table(name = "UserRecipeConsumed")
public class UserRecipeConsumed {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    //UserRecipe-ID -> Foreign Key
    @JoinColumn(name = "user_recipe_id", referencedColumnName = "id")
    @ManyToOne
    private UserRecipe recipeId;

    @NotNull
    @Column(name = "date")
    @Basic(optional = false)
    private Date date;

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
     Checks if the User object identified by 'object' is the same as the User object identified by 'id'
     Parameter object = User object identified by 'object'
     Returns True if the User 'object' and 'id' are the same; otherwise, return False
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

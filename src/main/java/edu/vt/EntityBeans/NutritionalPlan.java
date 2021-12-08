package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
// Name of the database table represented
@Table(name = "NutritionalPlan")
public class NutritionalPlan {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "daily_calorie_intake")
    private double dailyCalorieIntake;

    @Basic(optional = false)
    @NotNull
    @Column(name = "daily_calorie_burn")
    private double dailyCalorieBurn;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "workout_names")
    private String workoutNames;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "recipe_names")
    private String recipeNames;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "recipe_ids")
    private String recipeIds;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "workout_ids")
    private String workoutIds;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "description")
    private String description;

    public NutritionalPlan() {
    }

    public NutritionalPlan(Integer id) {
        this.id = id;
    }

    public NutritionalPlan(Integer id, String name, double dailyCalorieIntake, double dailyCalorieBurn,
                           String workoutNames, String recipeNames, String recipeIds, String workoutIds,
                           String description) {
        this.id = id;
        this.name = name;
        this.dailyCalorieIntake = dailyCalorieIntake;
        this.dailyCalorieBurn = dailyCalorieBurn;
        this.workoutNames = workoutNames;
        this.recipeNames = recipeNames;
        this.recipeIds = recipeIds;
        this.workoutIds = workoutIds;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(double dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public double getDailyCalorieBurn() {
        return dailyCalorieBurn;
    }

    public void setDailyCalorieBurn(double dailyCalorieBurn) {
        this.dailyCalorieBurn = dailyCalorieBurn;
    }

    public String getWorkoutNames() {
        return workoutNames;
    }

    public void setWorkoutNames(String workoutNames) {
        this.workoutNames = workoutNames;
    }

    public String getRecipeNames() {
        return recipeNames;
    }

    public void setRecipeNames(String recipeNames) {
        this.recipeNames = recipeNames;
    }

    public String getRecipeIds() {
        return recipeIds;
    }

    public void setRecipeIds(String recipeIds) {
        this.recipeIds = recipeIds;
    }

    public String getWorkoutIds() {
        return workoutIds;
    }

    public void setWorkoutIds(String workoutIds) {
        this.workoutIds = workoutIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the NutritionalPlan object identified by 'object' is the same as the NutritionalPlan object identified by 'id'
     Parameter object = NutritionalPlan object identified by 'object'
     Returns True if the NutritionalPlan 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof NutritionalPlan)) {
            return false;
        }
        NutritionalPlan other = (NutritionalPlan) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}

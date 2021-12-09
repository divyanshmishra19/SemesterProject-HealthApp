/*
 * Created by Team 10 on 2021.12.08
 * Copyright © 2021 Team 10. All rights reserved.
 */
package edu.vt.EntityBeans;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/*
The @Entity annotation designates this class as a JPA Entity POJO class
representing the Recipe table in the HealthTechDB database.
 */
@Entity
// Name of the database table represented
@Table(name = "Recipe")
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    ========================================================
    Instance variables representing the attributes (columns)
    of the Nutritional Plan table in the HealthTechDB database.

    CREATE TABLE Recipe
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(256) NOT NULL,
    calories DECIMAL(8,4) NOT NULL,
    fat_total DECIMAL(8,4) NOT NULL,
    fat_sat DECIMAL(8,4) NOT NULL,
    fat_trans DECIMAL(8,4) NOT NULL,
    fat_mono DECIMAL(8,4) NOT NULL,
    fat_poly DECIMAL(8,4) NOT NULL,
    carbs DECIMAL(8,4) NOT NULL,
    protein DECIMAL(8,4) NOT NULL,
    fat_cal DECIMAL(8,4) NOT NULL,
    carb_cal DECIMAL(8,4) NOT NULL,
    protein_cal DECIMAL(8,4) NOT NULL,
    sodium DECIMAL(8,4) NOT NULL,
    calcium DECIMAL(8,4) NOT NULL,
    magnesium DECIMAL(8,4) NOT NULL,
    potassium DECIMAL(8,4) NOT NULL,
    iron DECIMAL(8,4) NOT NULL,
    zinc DECIMAL(8,4) NOT NULL,
    diet_labels VARCHAR(2048) NOT NULL,
    ingredients VARCHAR(2048) NOT NULL
);
    ========================================================
            */

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
    @Column(name = "calories")
    private double calories;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_total")
    private double fatTotal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_sat")
    private double fatSat;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_trans")
    private double fatTrans;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_mono")
    private double fatMono;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_poly")
    private double fatPoly;

    @Basic(optional = false)
    @NotNull
    @Column(name = "carbs")
    private double carbs;

    @Basic(optional = false)
    @NotNull
    @Column(name = "protein")
    private double protein;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fat_cal")
    private double fatCal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "carb_cal")
    private double carbCal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "protein_cal")
    private double proteinCal;

    @Basic(optional = false)
    @NotNull
    @Column(name = "sodium")
    private double sodium;

    @Basic(optional = false)
    @NotNull
    @Column(name = "calcium")
    private double calcium;

    @Basic(optional = false)
    @NotNull
    @Column(name = "magnesium")
    private double magnesium;

    @Basic(optional = false)
    @NotNull
    @Column(name = "potassium")
    private double potassium;

    @Basic(optional = false)
    @NotNull
    @Column(name = "iron")
    private double iron;

    @Basic(optional = false)
    @NotNull
    @Column(name = "zinc")
    private double zinc;

    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 2048)
    @Column(name = "diet_labels")
    private String dietLabels;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "ingredients")
    private String ingredients;

    /*
    ==========================================================================
    Class constructors for instantiating a Recipe entity object to
    represent a row in the NutritionalPlan table in the HealthTechDB database.
    ==========================================================================
     */
    public Recipe() {
    }

    public Recipe(Integer id) {
        this.id = id;
    }

    public Recipe(Integer id, String name, double calories, double fatTotal, double fatSat, double fatMono, double fatTrans,
                  double fatPoly, double carbs, double protein, double fatCal, double carbCal, double proteinCal,
                  double sodium, double calcium, double magnesium, double potassium, double iron, double zinc,
                  String dietLabels, String ingredients) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.fatTotal = fatTotal;
        this.fatSat = fatSat;
        this.fatMono = fatMono;
        this.fatTrans = fatTrans;
        this.fatPoly = fatPoly;
        this.carbs = carbs;
        this.protein = protein;
        this.fatCal = fatCal;
        this.carbCal = carbCal;
        this.proteinCal = proteinCal;
        this.sodium = sodium;
        this.calcium = calcium;
        this.magnesium = magnesium;
        this.potassium = potassium;
        this.iron = iron;
        this.zinc = zinc;
        this.dietLabels = dietLabels;
        this.ingredients = ingredients;
    }

    /*
    ========================================================
    Getter and Setter methods for the attributes (columns)
    of the Recipe table in the HealthTechDB database.
    ========================================================
     */

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

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFatTotal() {
        return fatTotal;
    }

    public void setFatTotal(double fatTotal) {
        this.fatTotal = fatTotal;
    }

    public double getFatSat() {
        return fatSat;
    }

    public void setFatSat(double fatSat) {
        this.fatSat = fatSat;
    }

    public double getFatMono() {
        return fatMono;
    }

    public void setFatMono(double fatMono) {
        this.fatMono = fatMono;
    }

    public double getFatPoly() {
        return fatPoly;
    }

    public void setFatPoly(double fatPoly) {
        this.fatPoly = fatPoly;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFatCal() {
        return fatCal;
    }

    public void setFatCal(double fatCal) {
        this.fatCal = fatCal;
    }

    public double getCarbCal() {
        return carbCal;
    }

    public void setCarbCal(double carbCal) {
        this.carbCal = carbCal;
    }

    public double getProteinCal() {
        return proteinCal;
    }

    public void setProteinCal(double proteinCal) {
        this.proteinCal = proteinCal;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(double magnesium) {
        this.magnesium = magnesium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public double getZinc() {
        return zinc;
    }

    public void setZinc(double zinc) {
        this.zinc = zinc;
    }

    public String getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(String dietLabels) {
        this.dietLabels = dietLabels;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public double getFatTrans() {
        return fatTrans;
    }

    public void setFatTrans(double fatTrans) {
        this.fatTrans = fatTrans;
    }

    // Generate and return a hash code value for the object with database primary key id
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /*
     Checks if the Recipe object identified by 'object' is the same as the Recipe object identified by 'id'
     Parameter object = Recipe object identified by 'object'
     Returns True if the Recipe 'object' and 'id' are the same; otherwise, return False
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    // Return String representation of database primary key id
    @Override
    public String toString() {
        return id.toString();
    }
}

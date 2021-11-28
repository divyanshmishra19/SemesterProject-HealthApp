package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Recipe implements Serializable {

    private int id;
    private String name;
    private int calories;
    private float fatTotal;
    private float fatSat;
    private float fatMono;
    private float fatPoly;
    private float carbs;
    private float protein;

    private int fatCal;
    private int carbCal;
    private int proteinCal;

    private float sodium;
    private float calcium;
    private float magnesium;
    private float potassium;
    private float iron;
    private float zinc;

    private String dietLabels;
    private String ingredients;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDietLabels() {
        return dietLabels.toString();
    }

    public void setDietLabels(String dietLabels) {
        this.dietLabels = dietLabels;
    }


    public String getIngredients() {
        return ingredients.toString();
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public float getFatTotal() {
        return fatTotal;
    }

    public void setFatTotal(float fatTotal) {
        this.fatTotal = fatTotal;
    }

    public float getFatSat() {
        return fatSat;
    }

    public void setFatSat(float fatSat) {
        this.fatSat = fatSat;
    }

    public float getFatMono() {
        return fatMono;
    }

    public void setFatMono(float fatMono) {
        this.fatMono = fatMono;
    }

    public float getFatPoly() {
        return fatPoly;
    }

    public void setFatPoly(float fatPoly) {
        this.fatPoly = fatPoly;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public int getFatCal() {
        return fatCal;
    }

    public void setFatCal(int fatCal) {
        this.fatCal = fatCal;
    }

    public int getCarbCal() {
        return carbCal;
    }

    public void setCarbCal(int carbCal) {
        this.carbCal = carbCal;
    }

    public int getProteinCal() {
        return proteinCal;
    }

    public void setProteinCal(int proteinCal) {
        this.proteinCal = proteinCal;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getCalcium() {
        return calcium;
    }

    public void setCalcium(float calcium) {
        this.calcium = calcium;
    }

    public float getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(float magnesium) {
        this.magnesium = magnesium;
    }

    public float getPotassium() {
        return potassium;
    }

    public void setPotassium(float potassium) {
        this.potassium = potassium;
    }

    public float getIron() {
        return iron;
    }

    public void setIron(float iron) {
        this.iron = iron;
    }

    public float getZinc() {
        return zinc;
    }

    public void setZinc(float zinc) {
        this.zinc = zinc;
    }

}

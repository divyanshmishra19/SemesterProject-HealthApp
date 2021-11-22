package edu.vt.EntityBeans;

import java.util.HashMap;

public class Recipe {


    private int calories;
    private float yield;
    private float totalWeight;
    private String[] healthLabels;
    private String[] dietLabels;
    private String[] cautions;
    private HashMap<String, Nutrition> totalNutrients;
    private String uri;

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getYield() {
        return yield;
    }

    public void setYield(float yield) {
        this.yield = yield;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String[] getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String[] healthLabels) {
        this.healthLabels = healthLabels;
    }

    public String[] getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(String[] dietLabels) {
        this.dietLabels = dietLabels;
    }

    public String[] getCautions() {
        return cautions;
    }

    public void setCautions(String[] cautions) {
        this.cautions = cautions;
    }

    public HashMap<String, Nutrition> getTotalNutrients() {
        return totalNutrients;
    }

    public void setTotalNutrients(HashMap<String, Nutrition> totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    class Nutrition{
        private String label;
        private float quantity;
        private String unit;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public float getQuantity() {
            return quantity;
        }

        public void setQuantity(float quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}

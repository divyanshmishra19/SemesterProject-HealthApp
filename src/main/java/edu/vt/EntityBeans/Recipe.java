package edu.vt.EntityBeans;

import java.util.HashMap;
import java.util.List;

public class Recipe {

    private int id;
    private int name;
    private int calories;
    private float yield;
    private float totalWeight;
    private List<String> healthLabels;
    private List<String> dietLabels;
    private List<String> cautions;
    private HashMap<String, Nutrition> totalNutrients;
    private String uri;


    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

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

    public String getHealthLabels() {
        return healthLabels.toString();
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }

    public String getDietLabels() {
        return dietLabels.toString();
    }

    public void setDietLabels(List<String> dietLabels) {
        this.dietLabels = dietLabels;
    }

    public List<String> getCautions() {
        return cautions;
    }

    public void setCautions(List<String> cautions) {
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

    public float getFat() {
        return totalNutrients.get("FAT").getQuantity();
    }

    public float getProtein() {
        return totalNutrients.get("PROCNT").getQuantity();
    }

    public float getCarbs() {
        return totalNutrients.get("CHOCDF").getQuantity();
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

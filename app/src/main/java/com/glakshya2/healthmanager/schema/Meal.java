package com.glakshya2.healthmanager.schema;

public class Meal {
    String name;
    float carbohydrates;
    float protein;
    float fat;
    float calories;

    public Meal() {
    }

    public Meal(String name, float carbohydrates, float protein, float fat, float calories) {
        this.name = name;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.fat = fat;
        this.calories = calories;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

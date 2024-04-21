package com.glakshya2.healthmanager.schema;

import java.util.ArrayList;

public class Nutrition {
    ArrayList<Meal> mealList = new ArrayList<>();

    public Nutrition(ArrayList<Meal> mealList) {
        this.mealList = mealList;
    }

    public Nutrition() {
    }

    public ArrayList<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(ArrayList<Meal> mealList) {
        this.mealList = mealList;
    }
}

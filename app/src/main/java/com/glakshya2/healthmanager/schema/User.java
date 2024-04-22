package com.glakshya2.healthmanager.schema;

public class User {
    private Profile profile;
    private Nutrition nutrition;
    private History history;

    public User(Profile profile, Nutrition nutrition, History history) {
        this.profile = profile;
        this.nutrition = nutrition;
        this.history = history;
    }

    public User() {
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

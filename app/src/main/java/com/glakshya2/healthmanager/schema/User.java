package com.glakshya2.healthmanager.schema;

public class User {
    private Profile profile;
    private Nutrition nutrition;

    public User(Profile profile, Nutrition nutrition) {
        this.profile = profile;
        this.nutrition = nutrition;
    }

    public User() {
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

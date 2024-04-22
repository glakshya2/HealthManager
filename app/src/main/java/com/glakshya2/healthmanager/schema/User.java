package com.glakshya2.healthmanager.schema;

public class User {
    private Profile profile;
    private Nutrition nutrition;
    private History history;
    private ReminderList reminderList;

    public User(Profile profile, Nutrition nutrition, History history, ReminderList reminderList) {
        this.profile = profile;
        this.nutrition = nutrition;
        this.history = history;
        this.reminderList = reminderList;
    }

    public User() {
    }

    public ReminderList getReminderList() {
        return reminderList;
    }

    public void setReminderList(ReminderList reminderList) {
        this.reminderList = reminderList;
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

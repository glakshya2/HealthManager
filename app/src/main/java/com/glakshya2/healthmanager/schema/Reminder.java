package com.glakshya2.healthmanager.schema;

public class Reminder {
    private String title;
    private String description;
    private String dateTime;
    private boolean isRecurring;
    private String recurrenceType;
    private boolean isActive;

    public Reminder() {
    }

    public Reminder(String title, String description, String dateTime, boolean isRecurring, String recurrenceType, boolean isActive) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.isRecurring = isRecurring;
        this.recurrenceType = recurrenceType;
        this.isActive = isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(String recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

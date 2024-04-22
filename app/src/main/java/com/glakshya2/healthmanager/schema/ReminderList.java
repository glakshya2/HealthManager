package com.glakshya2.healthmanager.schema;

import java.util.ArrayList;

public class ReminderList {
    ArrayList<Reminder> reminderArrayList = new ArrayList<Reminder>();

    public ReminderList() {
    }

    public ReminderList(ArrayList<Reminder> reminderArrayList) {
        this.reminderArrayList = reminderArrayList;
    }

    public ArrayList<Reminder> getReminderArrayList() {
        return reminderArrayList;
    }

    public void setReminderArrayList(ArrayList<Reminder> reminderArrayList) {
        this.reminderArrayList = reminderArrayList;
    }
}

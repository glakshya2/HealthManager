package com.glakshya2.healthmanager.schema;

import java.util.ArrayList;

public class Record {
    String name, type, date, description;
    ArrayList<Files> filesArrayList = new ArrayList<Files>();

    public Record() {
    }

    public Record(String name, String type, String date, String description, ArrayList<Files> filesArrayList) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.description = description;
        this.filesArrayList = filesArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Files> getFiles() {
        return filesArrayList;
    }

    public void setFiles(ArrayList<Files> filesArrayList) {
        this.filesArrayList = filesArrayList;
    }
}

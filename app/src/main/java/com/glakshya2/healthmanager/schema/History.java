package com.glakshya2.healthmanager.schema;

import java.util.ArrayList;

public class History {
    ArrayList<Record> recordArrayList = new ArrayList<Record>();

    public History() {
    }

    public History(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }

    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }
}

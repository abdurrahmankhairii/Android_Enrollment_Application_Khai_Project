package com.example.androidenrollmentapplication;

import java.io.Serializable;

public class DataSubject implements Serializable {
    private String name;
    private int credits;

    public DataSubject() {
        // Default constructor required for calls to DataSnapshot.getValue(DataSubject.class)
    }

    public DataSubject(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}

package com.example.androidenrollmentapplication;

import java.util.List;

public class DataEnroll {

    private List<DataSubject> subjects;
    private int totalCredits;

    public DataEnroll() {
        // Default constructor required for calls to DataSnapshot.getValue(DataEnroll.class)
    }

    public DataEnroll(List<DataSubject> subjects, int totalCredits) {
        this.subjects = subjects;
        this.totalCredits = totalCredits;
    }

    public List<DataSubject> getSubjects() {
        return subjects;
    }

    public int getTotalCredits() {
        return totalCredits;
    }


}

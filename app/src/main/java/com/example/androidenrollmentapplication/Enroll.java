package com.example.androidenrollmentapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Enroll extends AppCompatActivity {

    // UI elements
    private LinearLayout subjectListLayout;
    private TextView totalCreditsTextView;
    private Button buttonLogOut, buttonSubmit;
    private List<DataSubject> subjects;
    private int totalCredits = 0;
    private DocumentReference enrollmentRef; // Reference to the enrollment document
    private static final int MAX_CREDITS = 24; // Maximum credits allowed
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Enable edge-to-edge layout
        setContentView(R.layout.activity_enroll); // Set the content view

        // Set up window insets for proper padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize UI elements
        subjectListLayout = findViewById(R.id.subjectListLayout);
        totalCreditsTextView = findViewById(R.id.totalCreditsTextView);
        buttonLogOut = findViewById(R.id.buttonLogOut);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        db = FirebaseFirestore.getInstance(); // Initialize Firestore instance

        // Load subjects from Firestore
        loadSubjects();

        // Set up Log-Out button click listener
        buttonLogOut.setOnClickListener(view -> {
            // Log out logic (Firebase)
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Enroll.this, MainActivity.class);
            startActivity(intent); // Navigate back to MainActivity
            finish(); // Close the current activity
        });

        // Set up submit button click listener
        buttonSubmit.setOnClickListener(view -> {
            submitSelectedSubjects();
        });
    }

    //
    private void submitSelectedSubjects() {
        List<DataSubject> selectedSubjects = new ArrayList<>();
        // Iterate through the existing checkboxes in the subjectListLayout
        for (int i = 0; i < subjectListLayout.getChildCount(); i++) {
            View child = subjectListLayout.getChildAt(i);
            if (child instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) child;
                if (checkBox.isChecked()) {
                    // If the checkbox is checked, add the corresponding subject to the list
                    selectedSubjects.add(subjects.get(i)); // Get the subject based on the index
                }
            }
        }

        // Check if any subjects were selected
        if (selectedSubjects.isEmpty()) {
            Toast.makeText(this, "Please select at least one subject.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save selected subjects to Firestore
        DocumentReference enrollmentRef = db.collection("Enrollments").document(); // Create a new document
        enrollmentRef.set(new DataEnroll(selectedSubjects, totalCredits))
                .addOnSuccessListener(aVoid -> {
                    // Get the document ID after successfully saving
                    String enrollmentDocumentId = enrollmentRef.getId(); // Get the document ID
                    // Navigate to EnrollmentSummary
                    Intent intent = new Intent(Enroll.this, EnrollSummary.class);
                    // Pass the array of selected subjects directly
                    intent.putExtra("selectedSubjects", selectedSubjects.toArray(new DataSubject[0])); // No need to cast to Serializable
                    intent.putExtra("totalCredits", totalCredits);
                    intent.putExtra("enrollmentDocumentId", enrollmentDocumentId);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Enroll.this, "Error saving enrollment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    // Method to load subjects from Firestore
    private void loadSubjects() {
        CollectionReference subjectsRef = db.collection("Subjects"); // Reference to the subjects collection
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    subjects = new ArrayList<>(); // Initialize the subjects list
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Retrieve subject name and credits from Firestore
                        String name = document.getString("name");
                        int credits = document.getLong("credits").intValue();
                        subjects.add(new DataSubject(name, credits)); // Create Subject object and add to the list
                    }
                    displaySubjects(); // Display the loaded subjects
                } else {
                    // Show error message if the task fails
                    Toast.makeText(Enroll.this, "Error getting subjects: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to display subjects as checkboxes
    private void displaySubjects() {
        for (DataSubject subject : subjects) {
            CheckBox checkBox = new CheckBox(this); // Create a new checkbox for each subject
            checkBox.setText(subject.getName() + " (" + subject.getCredits() + " credits)"); // Set checkbox text
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // If checked, check if adding credits exceeds the limit
                    if (totalCredits + subject.getCredits() > MAX_CREDITS) {
                        Toast.makeText(Enroll.this, "Cannot exceed " + MAX_CREDITS + " credits.", Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false); // Uncheck if limit exceeded
                    } else {
                        totalCredits += subject.getCredits(); // Add credits if within limit
                    }
                } else {
                    totalCredits -= subject.getCredits(); // Subtract credits if unchecked
                }
                updateTotalCredits(); // Update the total credits display
            });
            subjectListLayout.addView(checkBox); // Add checkbox to the layout
        }
    }

    // Method to update the total credits display
    private void updateTotalCredits() {
        totalCreditsTextView.setText("Total Credits: " + totalCredits); // Update the TextView with total credits
    }



}

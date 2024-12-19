package com.example.androidenrollmentapplication;

import android.os.Bundle;
import android.content.Intent;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.View;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    // Login Page Activity

    private Button buttonLogin, buttonRegister;
    private EditText txtEmail, txtPassword;
    private TextView khaiProjectText;
    private Typeface typeface;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    // Find your TextView
        TextView khaiProjectText = findViewById(R.id.khaiProjectText);

        // Load the font from the res/font folder
        Typeface typeface = ResourcesCompat.getFont(this, R.font.montserrat_bold);

        // Apply the font to the TextView
        khaiProjectText.setTypeface(typeface);

        // Initialize Views and Buttons
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();

        // Login Function Logic
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    txtEmail.setError("Please enter an email address.");
                    return;
                } else if (TextUtils.isEmpty(password)){
                    txtPassword.setError("Please enter a password.");
                    return;
                } else {
                    loginUser(email, password);
                }
            }
        });

        // Register Function Logic
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser(String email, String password){
        // Proceed with Firebase login
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Hi! Welcome, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        // Navigate to DashboardActivity
                        Intent intent = new Intent(MainActivity.this, Enroll.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    String errorMessage = task.getException() != null ? task.getException().getMessage() : "Login failed.";
                    Toast.makeText(MainActivity.this, "Incorrect email or password: ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
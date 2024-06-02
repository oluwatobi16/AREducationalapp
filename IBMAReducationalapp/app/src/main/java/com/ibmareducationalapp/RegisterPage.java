package com.ibmareducationalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



//import com.ibmareducationalapp.Models.RegistrationRequest;

public class RegisterPage extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword, editTextUniversityEmail, editTextConfirmPassword, editTextAcademicsubject;
    private View alreadyRegistered; // Declare the variable for already registered text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        editTextUsername = findViewById(R.id.editTextUsernameRegister);
        editTextPassword = findViewById(R.id.editTextPasswordRegister);
        editTextAcademicsubject = findViewById(R.id.editTextAcademicsubject);
        editTextUniversityEmail = findViewById(R.id.editTextUniversityEmail);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        alreadyRegistered = findViewById(R.id.AlreadyRegistered); // Initialize already registered text
        Button buttonRegister = findViewById(R.id.buttonRegister);
        Button buttonBackToLogin = findViewById(R.id.buttonBackToLogin); // Add reference to the back to login button

        // Set a click listener for the register button
        buttonRegister.setOnClickListener(view -> {

            // Retrieve entered data
            String username = editTextUsername.getText().toString();
            String password = editTextPassword.getText().toString();
            String academicsubject = editTextAcademicsubject.getText().toString();
            //String universityEmail = editTextUniversityEmail.getText().toString();
            String universityEmail = editTextUniversityEmail.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString();

            // Regular expression pattern for the email
            String emailPattern = "[a-zA-Z]{4}\\d{2}@durham\\.ac\\.uk";


            // Basic confirmation logic, checking if the password strings match
            if (password.equals(confirmPassword)) {
                // Check if the email matches the pattern
                if (universityEmail.matches(emailPattern)) {
                    //RegistrationRequest registrationRequest = new RegistrationRequest(username, academicsubject, universityEmail, password);
                    ApiService apiService = RetrofitClient.getInstance().getApi();
                    apiService.registerUser(username,academicsubject,universityEmail,password).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_LONG).show();
                                // Additional success logic here, if needed
                            } else {
                                Toast.makeText(RegisterPage.this, "Registration failed", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterPage.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                    // Clear all input fields
                    editTextUsername.setText("");
                    editTextPassword.setText("");
                    editTextAcademicsubject.setText("");
                    editTextUniversityEmail.setText("");
                    editTextConfirmPassword.setText("");

                    // Hide input fields
                    editTextUsername.setVisibility(View.GONE);
                    editTextPassword.setVisibility(View.GONE);
                    editTextUniversityEmail.setVisibility(View.GONE);
                    editTextAcademicsubject.setVisibility(View.GONE);
                    editTextConfirmPassword.setVisibility(View.GONE);

                    // Hide the register button
                    buttonRegister.setVisibility(View.GONE);
                    alreadyRegistered.setVisibility(View.GONE); // Hide the already registered text

                    // Display registration successful message
                    Toast.makeText(RegisterPage.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Show back to login button
                    buttonBackToLogin.setVisibility(View.VISIBLE);
                } else {
                    // Display error message if email validation fails
                    Toast.makeText(RegisterPage.this, "Invalid email format. Please enter a valid Durham University email.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Display error message if password confirmation fails
                Toast.makeText(RegisterPage.this, "Passwords do not match. Please check your details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener for the back to login button
        buttonBackToLogin.setOnClickListener(view -> {
            // Navigate back to the MainActivity (login page)
            startActivity(new Intent(RegisterPage.this, MainActivity.class));
            finish(); // Finish this activity so that pressing back button doesn't bring back this activity
        });
    }
}


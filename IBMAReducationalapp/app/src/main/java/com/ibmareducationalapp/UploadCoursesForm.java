package com.ibmareducationalapp;
import com.ibmareducationalapp.Models.Course;
import com.ibmareducationalapp.Models.Location;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.Toast;

public class UploadCoursesForm extends AppCompatActivity {

    private EditText editTextCourseName, editTextCourseLink, editTextCourseDescription;
    private Button buttonUploadCourses, buttonBackToIntro;
    private TextView textViewTitle, textViewCharacterCount, Form_explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadcoursesform);

        // Find views
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextCourseLink = findViewById(R.id.editTextCourseLink);
        editTextCourseDescription = findViewById(R.id.editTextCourseDescription);
        buttonUploadCourses = findViewById(R.id.buttonUploadCourses);
        buttonBackToIntro = findViewById(R.id.buttonBacktoIntro);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewCharacterCount = findViewById(R.id.textViewCharacterCount);
        Form_explain = findViewById(R.id.Form_explain);

        // Set click listener for the "Upload Courses" button
        buttonUploadCourses.setOnClickListener(v -> {
            String courseName = editTextCourseName.getText().toString();
            String courseLink = editTextCourseLink.getText().toString();
            String courseDescription = editTextCourseDescription.getText().toString();

            Course newCourse = new Course();
            newCourse.setName(courseName);
            newCourse.setLink(courseLink);
            newCourse.setDescription(courseDescription);
            newCourse.setAverageRating(0.0);
            //location storage
            SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
            float latitude = prefs.getFloat("Latitude", 0f); // Default to 0 if not found
            float longitude = prefs.getFloat("Longitude", 0f);
            Location userLocation = new Location(); // Assuming this is your custom Location class
            userLocation.setLatitude(latitude);
            userLocation.setLongitude(longitude);
            newCourse.setLocation(userLocation);

            ApiService apiService = RetrofitClient.getInstance().getApi();
            apiService.addCourse(newCourse).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // Notify success
                        runOnUiThread(() -> {
                            Toast.makeText(UploadCoursesForm.this, "Course uploaded successfully", Toast.LENGTH_LONG).show();
                            // Optionally, clear the fields or navigate away
                        });
                    } else {
                        // Handle request error
                        runOnUiThread(() -> Toast.makeText(UploadCoursesForm.this, "Failed to upload course", Toast.LENGTH_LONG).show());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Handle network error
                    runOnUiThread(() -> Toast.makeText(UploadCoursesForm.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show());
                }
            });
            // Hide the EditText elements
            editTextCourseName.setVisibility(View.GONE);
            editTextCourseLink.setVisibility(View.GONE);
            editTextCourseDescription.setVisibility(View.GONE);

            // Hide the "Upload Courses" button
            buttonUploadCourses.setVisibility(View.GONE);

            // Update the title text
            textViewTitle.setText(R.string.course_upload_thank_you);

            // Show the "Back to Intro" button
            buttonBackToIntro.setVisibility(View.VISIBLE);

            Form_explain.setVisibility(View.GONE);
        });

        // Set click listener for the "Back to Intro" button
        buttonBackToIntro.setOnClickListener(v -> {
            Intent intent = new Intent(UploadCoursesForm.this, IntroPage.class);
            // Start the intro activity
            startActivity(intent);
            // Finish the current activity
            finish();
        });

        // Set a TextWatcher on the EditText for the course description
        editTextCourseDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Unused
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Update the character count TextView
                updateCharacterCount(s.toString());
            }
        });
    }

    private void updateCharacterCount(String text) {
        // Get the character count string from resources and format it with the current text length
        String characterCountText = getString(R.string.character_count, text.length());
        // Set the formatted text to the TextView
        textViewCharacterCount.setText(characterCountText);
    }
}

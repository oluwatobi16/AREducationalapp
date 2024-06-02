package com.ibmareducationalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ibmareducationalapp.Models.Course;
import com.ibmareducationalapp.Models.Review;
import com.ibmareducationalapp.Models.ReviewDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseReviewPage extends AppCompatActivity {

    // UI components
    private Spinner spinnerCourses;
    private RatingBar ratingBar;
    private TextView textViewCourseName;
    private TextView textViewCourseLink;
    private TextView textViewCourseDescription;
    private EditText editTextReviewText;
    private Button buttonSubmitReview;

    // Adapter for the spinner
    private ArrayAdapter<String> adapter;

    // List to hold courses
    private List<Course> courses = new ArrayList<>();

    // ApiService instance for network calls
    private ApiService apiService;

    // Member variable to store the current rating
    private float currentRating = 0;

    // Define a hardcoded user ID for demonstration purposes
    private static final int HARDCODED_USER_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursereviewpage);

        // Initialize ApiService
        apiService = RetrofitClient.getInstance().getApi();

        // Binding UI components with their respective IDs
        bindUIComponents();

        // Initialize the spinner with courses
        initSpinner();

        // Set a listener for the spinner selection events
        setSpinnerListener();

        // Set a listener for rating bar changes
        setRatingBarListener();

        // Set a listener for the submit button click event
        setSubmitButtonListener();

        // Load courses from the server
        loadCourses();
    }

    // Bind all UI components to their views
    private void bindUIComponents() {
        spinnerCourses = findViewById(R.id.spinnerCourses);
        ratingBar = findViewById(R.id.ratingBar);
        textViewCourseName = findViewById(R.id.courseviewname);
        textViewCourseLink = findViewById(R.id.courseviewlink);
        textViewCourseDescription = findViewById(R.id.courseviewdescription);
        editTextReviewText = findViewById(R.id.editTextReview);

        buttonSubmitReview = findViewById(R.id.buttonSubmitReview);

    }

    // Initialize the spinner with courses
    private void initSpinner() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(adapter);
    }

    // Set spinner listener for item selection events
    private void setSpinnerListener() {
        spinnerCourses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = courses.get(position);
                displayCourseDetails(selectedCourse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle case when nothing is selected
            }
        });
    }

    // Set listener for rating bar changes
    private void setRatingBarListener() {
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser) {
                currentRating = rating; // Update the current rating member variable
            }
        });
    }

    // Set the listener for the submit review button click event
    private void setSubmitButtonListener() {
        buttonSubmitReview.setOnClickListener(v -> {
            Course selectedCourse = courses.get(spinnerCourses.getSelectedItemPosition());
            submitRating(selectedCourse, currentRating, editTextReviewText.getText().toString());
        });
    }

    // Load courses from the server
    private void loadCourses() {
        // Network call to fetch courses
        apiService.getAllCourses().enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    courses.clear();
                    courses.addAll(response.body());
                    updateSpinnerWithCourses();
                } else {
                    Toast.makeText(CourseReviewPage.this, "Failed to retrieve courses.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Toast.makeText(CourseReviewPage.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Update spinner with course names
    private void updateSpinnerWithCourses() {
        List<String> courseNames = new ArrayList<>();
        for (Course course : courses) {
            courseNames.add(course.getName());
        }
        adapter.clear();
        adapter.addAll(courseNames);
        adapter.notifyDataSetChanged();
    }

    // Display course details in the TextViews
    private void displayCourseDetails(Course course) {
        textViewCourseName.setText(course.getName());
        textViewCourseLink.setText(course.getLink());
        textViewCourseDescription.setText(course.getDescription());
    }

    // Submit rating for a course
    private void submitRating(Course course, float rating, String reviewText) {
        ReviewDTO reviewDTO = new ReviewDTO(HARDCODED_USER_ID, course.getId(), (int) rating, reviewText);

        // Send review to server
        apiService.addReview(reviewDTO).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CourseReviewPage.this, "Review submitted successfully", Toast.LENGTH_LONG).show();
                    // Reset the rating bar and review text
                    ratingBar.setRating(0);
                    editTextReviewText.setText("");
                } else {
                    Toast.makeText(CourseReviewPage.this, "Failed to submit review.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(CourseReviewPage.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
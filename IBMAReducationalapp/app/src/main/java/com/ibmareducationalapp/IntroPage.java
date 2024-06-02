package com.ibmareducationalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class IntroPage extends AppCompatActivity {
    private final ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null && data.getExtras() != null) {
                        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

                    }
                }
            });

    private static final int REQUEST_CAMERA_PERMISSION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intropage);

        Button buttonOpenCamera = findViewById(R.id.buttonOpenCamera);
        Button buttonAddCourses = findViewById((R.id.buttonUploadCourses));
        Button buttonReviewCourses = findViewById(R.id.buttonReviewCourses);

        buttonOpenCamera.setOnClickListener(v -> {
            // Checking camera permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                // Permission granted. Camera activated.
                startActivity(new Intent(IntroPage.this, ARfunctionality.class));
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "Requires camera privileges to scan buildings", Toast.LENGTH_LONG).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
        });
        buttonAddCourses.setOnClickListener(view -> {
            // Start the Upload Courses activity
            startActivity(new Intent(IntroPage.this, UploadCoursesForm.class));
        });
        buttonReviewCourses.setOnClickListener(view ->{
            //Start the Review page activity
            startActivity(new Intent(IntroPage.this, CourseReviewPage.class));
        });
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, AR fragment
                startActivity(new Intent(IntroPage.this, ARfunctionality.class));
            } else {
                // Permission denied, display a message to the user
                Toast.makeText(this, "Camera privileges denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

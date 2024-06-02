package com.ibmareducationalapp;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

// ar imports
import com.google.ar.core.Anchor;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;

// Sceneform classes for rendering 3D models in AR
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.rendering.ModelRenderable;

// Location imports
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.ar.sceneform.ux.TransformableNode;
import android.location.Location;
import android.os.Handler;

// main activity of page
public class ARfunctionality extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 1234;
    private static final long LOCATION_CHECK_INTERVAL = 10000; // 10 seconds
    private FusedLocationProviderClient fusedLocationClient;
    private Handler locationCheckHandler = new Handler();
    private Runnable locationCheckRunnable;
    private ArFragment arFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arfunctionality);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        Toast.makeText(getApplicationContext(), "ar session started", Toast.LENGTH_SHORT).show();

    }

    private boolean hasRequiredPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestNecessaryPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!hasRequiredPermissions()) {
            requestNecessaryPermissions();
        } else {
            if (arFragment != null && arFragment.getArSceneView().getSession() != null) {
                try {
                    arFragment.getArSceneView().getSession().resume();
                } catch (Exception e) {
                    Log.e("ARfunctionality", "Could not resume AR session", e);
                }
            }
            // Location checks
            Toast.makeText(getApplicationContext(), "Location checks starting", Toast.LENGTH_SHORT).show();
            startLocationChecks();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (arFragment != null) {
            arFragment.getArSceneView().getSession().pause();
        }
        stopLocationChecks();
    }

    private void startLocationChecks() {
        locationCheckRunnable = new Runnable() {
            @Override
            public void run() {
                getUserLocation();
                locationCheckHandler.postDelayed(this, LOCATION_CHECK_INTERVAL);
            }
        };
        locationCheckHandler.post(locationCheckRunnable);
    }

    private void stopLocationChecks() {
        locationCheckHandler.removeCallbacks(locationCheckRunnable);
    }

    private void getUserLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Use the location to check proximity to target locations and load AR objects
                                checkProximityAndLoadArObjects(location);
                            }
                        }
                    });
        }
    }

    private void checkProximityAndLoadArObjects(Location location) {
        // Fetch target locations from backend
        double[][] targetLocations = {
                {54.76726, -1.575462},
                {54.767988, -1.57334},
                {54.767520, -1.570252}
        };

        for (double[] targetLocation : targetLocations) {
            if (isUserNearTargetLocation(location, targetLocation[0], targetLocation[1])) {
                loadArObjectsForLocation(targetLocation[0], targetLocation[1]);
                stopLocationChecks(); // Stop further location checks once the user reaches a target location
                break; // Break so it only loads for first matching location
            }
        }
    }

    public List<String> fetchCoursesForLocation(double latitude, double longitude) {
        List<String> courses = new ArrayList<>();

        //Done via backend
        // Check if the coordinates match the specific location
        if (latitude == 54.76726 && longitude == -1.575462) {

            courses.add("AIFundamentals");
            courses.add("BuildingAISolutionsUsingAdvancedAlgos");
            courses.add("IBMAIEducation");
            courses.add("BuildingTrustworthyAIEnterpriseSolutions");
            courses.add("FundamentalsofSustainableTech");

        } else if (latitude == 54.767988 && longitude == -1.57334) {

            courses.add("FundamentalsofSustainableTech");
            courses.add("MasteringPromptWriting");
            courses.add("GettingstartedWThreatIntelligence");


        } else if (latitude == 54.767520 && longitude == -1.570252) {
            courses.add("GettingStartedWEnterpriseDataScience");
            courses.add("GettingstartedwithCloudfortheEnterprise");
            courses.add("AIFundamentals");
        }
        return courses;
    }

    private void loadArObjectsForLocation(Double latitude, Double longitude) {
        List<String> coursesAtLocation;
        coursesAtLocation = fetchCoursesForLocation(latitude, longitude);

        Session session = arFragment.getArSceneView().getSession();
        Toast.makeText(getApplicationContext(), "Displaying courses", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < coursesAtLocation.size(); i++) {
            String courseName = coursesAtLocation.get(i);
            String modelFileName = "models/" + courseName + ".glb";
            // the objects are displayed starting in the centre then the next is placed 10 metres to the right, then the next is ten metres to the left, and then 20 to the right etc
            int direction = (i % 2 == 0) ? 1 : -1; // Alternate direction
            int distance;
            if (i == 0) {
                distance = 0;
            } else {
                distance = 11 * ((i - 1) / 2 + 1) * direction; // Distance increases and alternates direction
            }

            // Offsets each model along the X axis for side-by-side placement and Z axis for depth with {0, 0, 0, 1} indicating no rotation
            Pose pose = new Pose(new float[]{distance, 0, -5 * ((i - 1) / 2 + 1)}, new float[]{0, 0, 0, 1});

            placeObject(modelFileName, pose);

        }
    }

    private void placeObject(String modelFileName, Pose pose) {

        final Handler handler = new Handler();
        Runnable checkTrackingAndPlaceObject = new Runnable() {
            @Override
            public void run() {
                Session session = arFragment.getArSceneView().getSession();
                if (session != null && arFragment.getArSceneView().getArFrame().getCamera().getTrackingState() == TrackingState.TRACKING) {
                    // Create an anchor at the specified pose
                    Anchor anchor = session.createAnchor(pose);
                    // Proceed with loading the model and placing it in the scene
                    ModelRenderable.builder()
                            .setSource(arFragment.getContext(), Uri.parse(modelFileName))
                            .setIsFilamentGltf(true)
                            .build()
                            .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                            .exceptionally(
                                    throwable -> {
                                        Log.e("ModelLoad", "Unable to load Renderable.", throwable);
                                        return null;
                                    });
                } else {
                    // Handle the case where tracking is not available
                    Log.e("ARFunctionality", "ARCore is not tracking, retrying...");
                    // Retry after a delay
                    handler.postDelayed(this, 2000); // Check again after 2 seconds
                }
            }
        };

        // Start the check
        handler.post(checkTrackingAndPlaceObject);
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode node = new TransformableNode(arFragment.getTransformationSystem());
        node.setRenderable(modelRenderable);
        node.setParent(anchorNode);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        node.select();
    }

    private boolean isUserNearTargetLocation(Location userLocation, double targetLatitude, double targetLongitude) {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(targetLatitude);
        targetLocation.setLongitude(targetLongitude);
        float distanceToTarget = userLocation.distanceTo(targetLocation);
        return distanceToTarget <= 45; // 45 metres is the proximity/radius threshold from the specified coordinates
    }
}


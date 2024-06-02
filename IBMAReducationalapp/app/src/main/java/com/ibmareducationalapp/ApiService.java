package com.ibmareducationalapp;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.Query;
import com.ibmareducationalapp.Models.*;

import java.util.List;

public interface ApiService {

    @GET("/user")
    Call <User> getUser(@Query("id") int id);
    
    @POST("/register")
    Call<Void> registerUser(@Query("name") String name,
                            @Query("academicSubject") String academicSubject,
                            @Query("email") String email,
                            @Query("password") String password);

    @POST("/applogin")
    Call<Void> loginUser(@Query("email")String email,@Query("password") String password);
    @GET("/courses/{id}")
    Call<Course> getCourseById(@Path("id") int courseId);

    @GET("/courses")
    Call<List<Course>> getAllCourses();

    @POST("/courses")
    Call<Void> addCourse(@Body Course course);

    @PUT("/courses/{id}")
    Call<Void> updateCourse(@Path("id") int courseId, @Body Course course);

    @DELETE("/courses/{id}")
    Call<Void> deleteCourse(@Path("id") int courseId);

    @GET("/armarkers")
    Call<List<Armarker>> getAllArMarkers();

    @GET("/armarkers/{id}")
    Call<Armarker> getArMarkerById(@Path("id") int arMarkerId);

    @POST("/armarkers")
    Call<Armarker> addArMarker(@Body Armarker arMarker);


    @PUT("/armarkers/{id}")
    Call<Armarker> updateArMarker(@Path("id") int arMarkerId, @Body Armarker arMarker);

    @DELETE("/armarkers/{id}")
    Call<Void> deleteArMarker(@Path("id") int arMarkerId);

    @POST("/reviews")
    Call<Review> addReview(@Body ReviewDTO reviewDTO);

    @GET("/reviews")
    Call<List<Review>> getAllReviews();


}

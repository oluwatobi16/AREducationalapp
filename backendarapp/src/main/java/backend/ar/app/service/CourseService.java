package backend.ar.app.service;

import backend.ar.app.model.Course;
import backend.ar.app.model.Location;

import backend.ar.app.Repository.CourseRepository;
import backend.ar.app.Repository.LocationRepository;
import backend.ar.app.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final LocationRepository locationRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, LocationRepository locationRepository,ReviewRepository reviewRepository){
        this.courseRepository = courseRepository;
        this.locationRepository = locationRepository;
        this.reviewRepository = reviewRepository;
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Integer id) {
        return courseRepository.findById(id);
    }

    public Course addCourse(Course course) {

        // check if the location already exists or needs to be created/updated
        Location location = course.getLocation();

        if (location != null && location.getId() == null) {
            locationRepository.save(location);
        }

        return courseRepository.save(course);
    }

    public Course updateCourse(Integer id, Course updatedCourse) {
        Course existingCourse = getCourseById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setLink(updatedCourse.getLink());
        existingCourse.setDescription(updatedCourse.getDescription());

        // Update the Location
        if (updatedCourse.getLocation() != null) {
            // If the course already has a location, update it
            Location existingLocation = existingCourse.getLocation();
            if (existingLocation != null) {
                // Update existing location fields
                existingLocation.setLatitude(updatedCourse.getLocation().getLatitude());
                existingLocation.setLongitude(updatedCourse.getLocation().getLongitude());
                locationRepository.save(existingLocation);
            } else {
                // If the course does not have an existing location, save the new one
                Location newLocation = locationRepository.save(updatedCourse.getLocation());
                existingCourse.setLocation(newLocation);
            }
        }

        // Save and return the updated course
        return courseRepository.save(existingCourse);
    }


    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }

    public void updateCourseAverageRating(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Double averageRating = reviewRepository.findAverageRatingByCourseId(courseId);
        // Set to 0 or another default value if null
        course.setAverageRating(Objects.requireNonNullElse(averageRating, 0.0));

        courseRepository.save(course);
    }


}

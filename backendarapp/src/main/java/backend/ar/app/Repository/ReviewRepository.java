package backend.ar.app.Repository;

import backend.ar.app.model.Course;
import backend.ar.app.model.Review;
import backend.ar.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // Define a method to find a Review by User and Course
    Optional<Review> findByUserAndCourse(User user, Course course);

    // Custom query to calculate the average rating of a course
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.course.id = :courseId")
    Double findAverageRatingByCourseId(int courseId);

    // Additional repository methods can be added here as needed
}

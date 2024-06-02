package backend.ar.app.service;

import backend.ar.app.model.Course;
import backend.ar.app.model.Review;
import backend.ar.app.model.ReviewDTO;
import backend.ar.app.model.User;

import backend.ar.app.Repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseService courseService;
    private final UserService userService; // Assuming CourseService has the method to update course average rating

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, CourseService courseService, UserService userService) { // Adjust constructor
        this.reviewRepository = reviewRepository;
        this.courseService = courseService;
        this.userService = userService; // Initialize UserService
    }


    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> findReviewById(int reviewId) {
        return reviewRepository.findById(reviewId);
    }

    @Transactional
    public Review saveReview(ReviewDTO reviewDTO) {
        User user = userService.getUserById(reviewDTO.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Course course = courseService.getCourseById(reviewDTO.getCourse_id())
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        // Check if the user has already reviewed the course
        Optional<Review> existingReview = reviewRepository.findByUserAndCourse(user, course);
        if (existingReview.isPresent()) {
            throw new IllegalStateException("User has already reviewed this course");
        }

        Review review = new Review();
        review.setUser(user);
        review.setCourse(course);
        review.setRating(reviewDTO.getRating());
        review.setReviewText(reviewDTO.getReviewText());

        // Save the review
        Review savedReview = reviewRepository.save(review);

        // Update the course average rating after successfully saving the review
        courseService.updateCourseAverageRating(reviewDTO.getCourse_id());

        return savedReview;
    }



    public void deleteReview(int reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        int courseId = review.getCourse().getId();
        reviewRepository.deleteById(reviewId);
        courseService.updateCourseAverageRating(courseId);
    }


}

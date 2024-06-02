package backend.ar.app.model;

public class ReviewDTO {
    private int user_id;
    private int course_id;
    private int rating;
    private String reviewText;

    public ReviewDTO(){}
    public ReviewDTO(int user_id, int course_id,int rating,String reviewText){
        this.user_id=user_id;
        this.course_id=course_id;
        this.rating=rating;
        this.reviewText=reviewText;
    }
    public int getUser_id(){return user_id;}
    public void setUser_id(int user_id){this.user_id=user_id;}
    public int getCourse_id(){return course_id;}
    public void setCourse_id(int course_id){this.course_id=course_id;}
    public int getRating(){return rating;}
    public void setRating(int rating){this.rating=rating;}
    public String getReviewText(){return reviewText;}
    public void setReviewText(String reviewText){this.reviewText=reviewText;}

}

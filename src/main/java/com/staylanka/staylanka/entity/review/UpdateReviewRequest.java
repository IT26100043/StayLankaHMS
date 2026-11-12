package com.staylanka.staylanka.entity.review;

public class UpdateReviewRequest {

    private int rating;
    private String comment;

    public UpdateReviewRequest(){}

    

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}

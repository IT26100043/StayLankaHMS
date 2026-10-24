package com.staylanka.staylanka.entity.review;

import java.time.LocalDate;

public class ReviewRequest {
    private Long customerId;
    private String roomId;
    private int rating;
    private String comment;
    private LocalDate date;


    public ReviewRequest(){}

    //getters

    public LocalDate getDate() {
        return date;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getRoomId() {
        return roomId;
    }

    //setters

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}

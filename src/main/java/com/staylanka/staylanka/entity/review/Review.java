package com.staylanka.staylanka.entity.review;

import com.staylanka.staylanka.entity.User;
import com.staylanka.staylanka.entity.room.Room;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId",nullable = false)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId",nullable = false)
    private Room room;

    private int rating;
    private String comment;
    private LocalDate Date;


    public Review(){
        this.rating = 0;
    }

    public Review(User user,Room room,int rating,String comment,LocalDate Date){
        this.comment = comment;
        this.Date = Date;
        this.user = user;
        this.room = room;
        this.rating = rating;
    }



    

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public Long getId() {
        return reviewId;
    }

    public LocalDate getDate() {
        return Date;
    }

    public Room getRoom() {
        return room;
    }

    public String getComment() {
        return comment;
    }

    


    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.reviewId = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

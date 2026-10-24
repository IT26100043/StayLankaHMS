package com.staylanka.staylanka.service.review;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.review.Review;
import com.staylanka.staylanka.entity.review.ReviewRequest;
import com.staylanka.staylanka.entity.review.UpdateReviewRequest;
import com.staylanka.staylanka.entity.room.Room;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import com.staylanka.staylanka.repository.review.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;


    public ReviewService(ReviewRepository reviewRepository , CustomerRepository customerRepository, RoomRepository roomRepository){
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewByUserId(Long id){
        return reviewRepository.findById(id);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
        System.out.println("delete successful");
    }

    public Review postNewReview(ReviewRequest reviewRequest){
        CustomerUser customerUser = customerRepository.findById(reviewRequest.getCustomerId()).orElseThrow();
        Room room = roomRepository.findById(reviewRequest.getRoomId()).orElseThrow();

        Review review = new Review(customerUser,room,reviewRequest.getRating(),reviewRequest.getComment(), LocalDate.now());

        return reviewRepository.save(review);

    }

    public Review UpdateReview(Long id, UpdateReviewRequest updateReviewRequest){
        Review existingReview = reviewRepository.findById(id).orElseThrow();

        if(updateReviewRequest.getComment() != null){
            existingReview.setComment(updateReviewRequest.getComment());
        }
        if (updateReviewRequest.getRating() != 0){
            existingReview.setRating(updateReviewRequest.getRating());
        }
        return reviewRepository.save(existingReview);
    }


}

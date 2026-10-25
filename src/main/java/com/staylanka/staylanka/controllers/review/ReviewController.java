package com.staylanka.staylanka.controllers.review;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.staylanka.staylanka.entity.review.Review;
import com.staylanka.staylanka.entity.review.ReviewRequest;
import com.staylanka.staylanka.entity.review.UpdateReviewRequest;
import com.staylanka.staylanka.service.review.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews")
    public List<Review> getAllReviews(){
        return reviewService.getAllReviews();
    }


    // GET /review/can-review?customerId=1&roomId=R101  ->  true / false
    @GetMapping("/can-review")
    public boolean canReview(@RequestParam Long customerId, @RequestParam String roomId){
        return reviewService.canReview(customerId, roomId);
    }

    @GetMapping("/{id}")
    public Optional<Review> getReviewById(@PathVariable Long id){
        return reviewService.getReviewByUserId(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        reviewService.deleteReview(id);
    }

    @PostMapping("/newreview")
    public Review newReview(@RequestBody ReviewRequest reviewRequest){
        return reviewService.postNewReview(reviewRequest);
    }

    @PutMapping("/{id}")
    public Review UpdateComment(@PathVariable Long id, @RequestBody UpdateReviewRequest updateReviewRequest){
        return reviewService.UpdateReview(id,updateReviewRequest);
    }

}
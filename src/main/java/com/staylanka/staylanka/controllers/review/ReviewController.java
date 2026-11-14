package com.staylanka.staylanka.controllers.review;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.staylanka.staylanka.entity.review.Review;
import com.staylanka.staylanka.entity.review.ReviewRequest;
import com.staylanka.staylanka.entity.review.UpdateReviewRequest;
import com.staylanka.staylanka.service.review.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping("/reviews/page")
    public Page<Review> getReviewsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) Integer rating) {
        int safePage = Math.max(0, page);
        int safeSize = Math.min(Math.max(1, size), 24);
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by(Sort.Direction.DESC, "reviewId"));
        return reviewService.getReviews(pageable, rating);
    }

    @GetMapping("/summary")
    public Map<String, Object> getReviewSummary(){
        return reviewService.getReviewSummary();
    }


    // GET /review/can-review?customerId=1&roomId=R101 returns true or false
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

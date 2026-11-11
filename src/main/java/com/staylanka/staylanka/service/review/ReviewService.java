package com.staylanka.staylanka.service.review;

import com.staylanka.staylanka.entity.CustomerUser;
import com.staylanka.staylanka.entity.review.Review;
import com.staylanka.staylanka.entity.review.ReviewRequest;
import com.staylanka.staylanka.entity.review.UpdateReviewRequest;
import com.staylanka.staylanka.entity.room.Room;
import com.staylanka.staylanka.repository.CustomerRepository;
import com.staylanka.staylanka.repository.Room.RoomRepository;
import com.staylanka.staylanka.repository.payment.PaymentRepository;
import com.staylanka.staylanka.repository.review.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final RoomRepository roomRepository;
    private final PaymentRepository paymentRepository;


    public ReviewService(ReviewRepository reviewRepository , CustomerRepository customerRepository, RoomRepository roomRepository, PaymentRepository paymentRepository){
        this.reviewRepository = reviewRepository;
        this.customerRepository = customerRepository;
        this.roomRepository = roomRepository;
        this.paymentRepository = paymentRepository;
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Page<Review> getReviews(Pageable pageable, Integer rating){
        return rating == null ? reviewRepository.findAll(pageable) : reviewRepository.findByRating(rating, pageable);
    }

    public Map<String, Object> getReviewSummary(){
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("total", reviewRepository.count());
        summary.put("average", reviewRepository.averageRating());
        Map<Integer, Long> distribution = new LinkedHashMap<>();
        for (int value = 5; value >= 1; value--) {
            distribution.put(value, reviewRepository.countByRating(value));
        }
        summary.put("distribution", distribution);
        return summary;
    }

    public Optional<Review> getReviewByUserId(Long id){
        return reviewRepository.findById(id);
    }

    public void deleteReview(Long id){
        reviewRepository.deleteById(id);
        System.out.println("delete successful");
    }

    // only a guest who paid for a stay in this room and has reached the check-in day may review it
    public boolean canReview(Long customerId, String roomId){
        if (customerId == null || roomId == null) {
            return false;
        }
        return paymentRepository.countCheckedInPaidStays(customerId, roomId, LocalDate.now()) > 0;
    }

    public Review postNewReview(ReviewRequest reviewRequest){
        if (!canReview(reviewRequest.getCustomerId(), reviewRequest.getRoomId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only guests who have paid for and checked in to this room can leave a review");
        }
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

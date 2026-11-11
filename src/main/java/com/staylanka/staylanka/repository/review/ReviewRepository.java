package com.staylanka.staylanka.repository.review;

import com.staylanka.staylanka.entity.review.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByRating(int rating, Pageable pageable);

    long countByRating(int rating);

    @Query("select coalesce(avg(r.rating), 0) from Review r")
    double averageRating();
}

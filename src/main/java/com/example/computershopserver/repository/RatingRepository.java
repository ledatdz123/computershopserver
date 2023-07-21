package com.example.computershopserver.repository;

import com.example.computershopserver.entity.Rating;
import com.example.computershopserver.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    @Query("SELECT avg(r.scores) FROM Rating r  WHERE r.ratingId.productId = :product_id")
    Optional<Float> findAVGRatingOfProduct(@Param("product_id") Long productId);

    Optional<List<Rating>> findRatingByRatingIdProductId(@Param("product_id") Long productId);
}

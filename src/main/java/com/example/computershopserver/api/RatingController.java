package com.example.computershopserver.api;

import com.example.computershopserver.dto.RequestRatingDto;
import com.example.computershopserver.dto.ResponseListRating;
import com.example.computershopserver.dto.ResponseRatingDto;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.service.impl.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;
    @PostMapping("/{id}/rating")
    @Operation(summary = "Rating products")
    public ResponseRatingDto addRatingForProduct(@PathVariable("id") Long id,
                                                 @RequestBody RequestRatingDto requestRatingDto) {
        return ratingService.addRating(requestRatingDto, id);
    }

    @GetMapping("/{id}/rating")
    @Operation(summary = "Get list rating of product")
    public List<ResponseListRating> getListRating(@PathVariable Long id) throws ResourceNotFoundException {
        return ratingService.getRatingsOfProduct(id);
    }

}


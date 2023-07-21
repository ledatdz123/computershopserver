package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.RequestRatingDto;
import com.example.computershopserver.dto.ResponseListRating;
import com.example.computershopserver.dto.ResponseRatingDto;
import com.example.computershopserver.exception.ResourceNotFoundException;

import java.util.List;

public interface RatingService {
    ResponseRatingDto addRating(RequestRatingDto requestRatingDto, Long productId);
    List<ResponseListRating> getRatingsOfProduct(Long productId) throws ResourceNotFoundException;
}


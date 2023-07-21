package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.RequestRatingDto;
import com.example.computershopserver.dto.ResponseListRating;
import com.example.computershopserver.dto.ResponseRatingDto;
import com.example.computershopserver.entity.Rating;
import com.example.computershopserver.entity.RatingId;
import com.example.computershopserver.entity.User;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.repository.RatingRepository;
import com.example.computershopserver.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ModelMapper modelMapper;

    UserRepository userRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository, UserRepository userRepository, ModelMapper modelMapper) {
        super();
        this.ratingRepository = ratingRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseRatingDto addRating(RequestRatingDto requestRatingDto, Long productId) {
        Rating rating = new Rating();
        RatingId ratingId = new RatingId(requestRatingDto.getUserId(), productId);
        rating.setScores(requestRatingDto.getScores());
        rating.setComment(requestRatingDto.getComment());
        rating.setRatingId(ratingId);
        rating = ratingRepository.save(rating);
        return modelMapper.map(rating, ResponseRatingDto.class);
    }

    @Override
    public List<ResponseListRating> getRatingsOfProduct(Long productId) throws ResourceNotFoundException {
        List<ResponseListRating> responseList = new ArrayList<>();
        List<Rating> ratingList = ratingRepository.findRatingByRatingIdProductId(productId).orElseThrow(() ->
                new ResourceNotFoundException("can't find list rating of product with id: " + productId));
        ratingList.forEach(rating -> {
            ResponseListRating responseListRating = modelMapper.map(rating, ResponseListRating.class);
            User user = userRepository.findById(rating.getRatingId().getUserId()).get();
            responseListRating.setEmail(user.getEmail());
            responseListRating.setUserId(user.getId());
            responseList.add(responseListRating);
        });
        return responseList;
    }
}


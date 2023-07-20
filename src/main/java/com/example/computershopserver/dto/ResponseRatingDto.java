package com.example.computershopserver.dto;

import com.example.computershopserver.entity.RatingId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRatingDto {

    private RatingId ratingId;
    private int scores;
    private String comment;
}

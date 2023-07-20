package com.example.computershopserver.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestRatingDto {
    @JsonProperty("user_id")
    private Long userId;
    private String comment;
    private int scores;
}


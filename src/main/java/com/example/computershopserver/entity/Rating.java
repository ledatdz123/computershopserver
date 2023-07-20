package com.example.computershopserver.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rating")
public class Rating {
    @EmbeddedId
    private RatingId ratingId;

    @Column(name = "scores")
    private float scores;

    @Column(name = "comment")
    private String comment;

}

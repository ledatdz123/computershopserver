package com.example.computershopserver.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String image;
    private Long categoryId;
    private String categoryName;
    private Long brandId;
    private String brandName;
    private float avgRating;
}

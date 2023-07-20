package com.example.computershopserver.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Product name is mandatory")
    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private Float price;
    private Integer quantity;
    private String image;
    @Column(name = "avg_rating")
    private float avgRating;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", nullable = false)
    private Brand brand;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<DetailOrder> detailOrders;
}


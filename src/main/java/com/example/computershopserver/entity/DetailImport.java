package com.example.computershopserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "details_import")
public class DetailImport{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "price")
    private Float price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idimport")
    private Imports imports;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproduct")
    private Product product;
}

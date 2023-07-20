package com.example.computershopserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    @Column(name = "ngaydat")
    private LocalDate ngaydat;
    @Column(name = "status")
    private String status;

    @Column(name = "total_price")
    private Float total_price;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;
    @Column(name="payment")
    private String payment;
    @ManyToOne
    @JoinColumn(name = "iduser")
    private User user;
    @OneToMany(fetch = FetchType.EAGER, mappedBy="order")
    private List<DetailOrder> detailOrders;
}

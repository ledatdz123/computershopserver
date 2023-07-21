package com.example.computershopserver.repository;

import com.example.computershopserver.entity.DetailOrder;
import com.example.computershopserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    List<DetailOrder> findOrderDetailsByOrder(Order order);
}

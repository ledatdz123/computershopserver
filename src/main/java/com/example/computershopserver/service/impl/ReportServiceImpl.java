package com.example.computershopserver.service.impl;

import com.example.computershopserver.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Object[]> getReportByDate(String startDate, String endDate) {
        return orderRepository.getReportbyDate(startDate, endDate);
    }

    @Override
    public List<Object[]> getTopFiveProduct() {
        return orderRepository.getTopFiveProduct();
    }

    @Override
    public List<Object[]> getTopFive() {
        return orderRepository.getTopFive();
    }
}

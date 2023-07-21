package com.example.computershopserver.service.impl;

import java.util.List;

public interface ReportService {
    List<Object[]> getReportByDate(String startDate, String endDate);
    List<Object[]> getTopFiveProduct();
    List<Object[]> getTopFive();
}


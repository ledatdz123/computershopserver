package com.example.computershopserver.service;

import com.example.computershopserver.dto.DetailOrderDTO;
import com.example.computershopserver.dto.OrderDetailResponseDTO;
import com.example.computershopserver.exception.AddDataFail;
import com.example.computershopserver.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DetailOrderService {
    public List<DetailOrderDTO> retrieveOrderDetails();

    public Optional<DetailOrderDTO> getOrderDetail(Long detailId) throws ResourceNotFoundException;

    //public DetailOrderDTO saveOrderDetail(DetailOrderDTO detailDTO) throws ResourceNotFoundException, AddDataFail;
    public List<DetailOrderDTO> saveOrderDetail(List<DetailOrderDTO> detailDTO) throws ResourceNotFoundException, AddDataFail;
    public Boolean deleteOrderDetail(Long detailId) throws ResourceNotFoundException;

    public DetailOrderDTO updateOrderDetail(Long id, DetailOrderDTO detailDTO) throws ResourceNotFoundException;

    public List<OrderDetailResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException;

    public DetailOrderDTO restoreQty(Long id) throws ResourceNotFoundException;

    public List<Object> getTopProduct();

    public String restoreCancelStatus(Long orderId) throws ResourceNotFoundException;
}


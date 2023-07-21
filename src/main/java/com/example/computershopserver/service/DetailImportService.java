package com.example.computershopserver.service;

import com.example.computershopserver.dto.CTPhieuNhapDTO;
import com.example.computershopserver.dto.CTPhieuNhapResponseDTO;
import com.example.computershopserver.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface DetailImportService {
    public List<CTPhieuNhapDTO> retrieveCTPNs();

    public Optional<CTPhieuNhapDTO> getCTPN(Long ctpnId) throws ResourceNotFoundException;

    public CTPhieuNhapDTO saveCTPN(CTPhieuNhapDTO nhapDTO) throws ResourceNotFoundException;

    public Boolean deleteCTPN(Long ctpnId) throws ResourceNotFoundException;

    public CTPhieuNhapDTO updateCTPN(Long ctpnId) throws ResourceNotFoundException;
    public List<CTPhieuNhapResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException;
}

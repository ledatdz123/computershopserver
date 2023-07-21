package com.example.computershopserver.service;

import com.example.computershopserver.dto.PhieuNhapDTO;
import com.example.computershopserver.dto.PhieuNhapResponseDTO;
import com.example.computershopserver.exception.ResourceNotFoundException;

import java.util.List;

public interface ImportsService {
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps();

//    public PhieuNhapResponseDTO getPhieuNhap(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException;

    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException;

    public PhieuNhapDTO updatePN(PhieuNhapDTO dto, Long id) throws ResourceNotFoundException;
}

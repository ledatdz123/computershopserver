package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.CTPhieuNhapDTO;
import com.example.computershopserver.dto.CTPhieuNhapResponseDTO;
import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.entity.DetailImport;
import com.example.computershopserver.entity.Imports;
import com.example.computershopserver.entity.Product;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.repository.DetailImportRepository;
import com.example.computershopserver.repository.ImportsRepository;
import com.example.computershopserver.repository.ProductRepository;
import com.example.computershopserver.service.DetailImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class DetailImportServieImpl implements DetailImportService {
    @Autowired
    private DetailImportRepository nhapRepository;
    @Autowired
    private ImportsRepository phieuNhapRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<CTPhieuNhapDTO> retrieveCTPNs() {
        List<DetailImport> nhaps = nhapRepository.findAll();

        return new CTPhieuNhapDTO().entityToDTO(nhaps);
    }

    @Override
    public Optional<CTPhieuNhapDTO> getCTPN(Long ctpnId) throws ResourceNotFoundException {
        DetailImport nhap = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"+ctpnId));
        return Optional.of(new CTPhieuNhapDTO().entityToDTO(nhap));
    }

    @Override
    public CTPhieuNhapDTO saveCTPN(CTPhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        DetailImport nhap = new CTPhieuNhapDTO().dtoToEntity(nhapDTO);
        Product product = productRepository.findById(nhapDTO.getIdproduct()).orElseThrow(() ->
                new ResourceNotFoundException("product not found for this id"+nhapDTO.getIdproduct()));
        product.setQuantity(product.getQuantity() + nhapDTO.getQuantity());
        productRepository.save(product);
        Imports pn = phieuNhapRepository.findById(nhapDTO.getIdimport()).orElse(null);
        nhap.setImports(pn);
        nhap.setProduct(product);
        return new CTPhieuNhapDTO().entityToDTO(nhapRepository.save(nhap));
    }

    @Override
    public Boolean deleteCTPN(Long ctpnId) throws ResourceNotFoundException {
        DetailImport nhap = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"));
        this.nhapRepository.delete(nhap);
        return true;
    }

    @Override
    public CTPhieuNhapDTO updateCTPN(Long ctpnId) throws ResourceNotFoundException {
        DetailImport nhapExist = nhapRepository.findById(ctpnId).orElseThrow(() -> new ResourceNotFoundException("phieu nhap not found for this id"));
        Product product = productRepository.findById(nhapExist.getProduct().getId()).orElseThrow(()-> new ResourceNotFoundException("phieu nhap not found for this id"));

        if(product.getQuantity() > nhapExist.getQuantity()) {
            product.setQuantity(product.getQuantity() - nhapExist.getQuantity());
            productRepository.save(product);
        }
        else{
            System.out.println("Quantity is not enought");
        }
        Imports pn = phieuNhapRepository.findById(nhapExist.getImports().getId()).orElse(null);
        nhapExist.setImports(pn);
        nhapExist.setProduct(product);
        DetailImport phieuNhap = new DetailImport();
        phieuNhap = nhapRepository.save(nhapExist);
        return new CTPhieuNhapDTO().entityToDTO(phieuNhap);
    }

    @Override
    public List<CTPhieuNhapResponseDTO> findDetailByOrder(Long orderId) throws ResourceNotFoundException {
        Optional<Imports> orderExist = phieuNhapRepository.findById(orderId);
        if(!orderExist.isPresent()){
            throw new ResourceNotFoundException(""+ ErrorCode.FIND_ORDER_ERROR);
        }
        Imports order = orderExist.get();

        List<DetailImport> list = null;
        list = nhapRepository.findDetailImportByImportsId(order);

        List<CTPhieuNhapResponseDTO> detailDTOS = new ArrayList<>();
        detailDTOS = new CTPhieuNhapResponseDTO().toListDto(list);
        return detailDTOS;
    }
}


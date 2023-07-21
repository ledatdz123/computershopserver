package com.example.computershopserver.service.impl;

import com.example.computershopserver.dto.PhieuNhapDTO;
import com.example.computershopserver.dto.PhieuNhapResponseDTO;
import com.example.computershopserver.entity.Imports;
import com.example.computershopserver.entity.User;
import com.example.computershopserver.exception.ResourceNotFoundException;
import com.example.computershopserver.repository.ImportsRepository;
import com.example.computershopserver.repository.ProductRepository;
import com.example.computershopserver.repository.UserRepository;
import com.example.computershopserver.service.ImportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class ImportsServiceImpl implements ImportsService {
    @Autowired
    private ImportsRepository nhapRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productrepository;

//    @Autowired
//    private CTPNhapRepository ctpNhapRepository;

    @Override
    public List<PhieuNhapResponseDTO> retrievePhieuNhaps() {
        List<Imports> nhaps = nhapRepository.findAll();

        return new PhieuNhapResponseDTO().toListDto(nhaps);
    }

    @Override
    public PhieuNhapDTO savePN(PhieuNhapDTO nhapDTO) throws ResourceNotFoundException {
        User user = userRepository.findById(nhapDTO.getIduser()).orElseThrow(() ->
                new ResourceNotFoundException("user not found for this id: "+nhapDTO.getIduser()));

        Imports nhap = new PhieuNhapDTO().dtoToEntity(nhapDTO);
        nhap.setUser(user);
        nhap.setNgaylapphieu(LocalDate.now());
        nhap.setStatus("complete");
        return new PhieuNhapDTO().entityToDTO(nhapRepository.save(nhap));
    }

    @Override
    public Boolean deletePN(Long nhapId) throws ResourceNotFoundException {
        Imports nhap = nhapRepository.findById(nhapId).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+nhapId));
        this.nhapRepository.delete(nhap);
        return true;
    }

    @Override
    public PhieuNhapDTO updatePN(PhieuNhapDTO dto, Long id) throws ResourceNotFoundException {
        Imports nhapExist = nhapRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+id));
        User user = userRepository.findById(dto.getIduser()).orElseThrow(() -> new ResourceNotFoundException("phieu dat not found for this id: "+dto.getIduser()));
        nhapExist.setNgaylapphieu(LocalDate.now());
        nhapExist.setUser(user);
        Imports nhap = new Imports();

        nhap = nhapRepository.save(nhapExist);
        return new PhieuNhapDTO().entityToDTO(nhap);
    }

//    @Override
//    public PhieuNhapDTO updateStatusPN(Long nhapId, String status) throws ResourceNotFoundException {
//        PhieuNhap nhapExist = nhapRepository.findById(nhapId).orElseThrow(() ->
//                new ResourceNotFoundException("phieu nhap not found for this id: "+nhapId));
//
//        if(status.equals("waiting receipt")){
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//            //Status sttDat = statusRepository.findAllByStatusName("complete");
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//        } else if(status.equals("receipted")){
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//        }
//        else if(status.equals("cancel")) {
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//
//            //Status sttDat = statusRepository.findAllByStatusName("no receipt");
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//
//        } else if(status.equals("complete")) {
//            Status stt = statusRepository.findAllByStatusName(status);
//            nhapExist.setStatus(stt);
//            List<CTPNhap> ctpNhapList = ctpNhapRepository.findAllByCtpnIdNhapId(nhapId);
//            ctpNhapList.forEach(n -> {
//                Product product2 = productrepository.getById(n.getCtpnId().getProductId());
//                System.out.println("product 2 id ========"+n.getCtpnId().getProductId());
//                product2.setProductQty(product2.getProductQty() + n.getQtyNhap());
//                productrepository.save(product2);
//                System.out.println("sl sau khi add"+product2.getProductQty());
//            });
//            nhapExist.getPhieuDat().setStatus(stt);
//            datRepository.save(nhapExist.getPhieuDat());
//        }
//
//        PhieuNhap nhap = new PhieuNhap();
//        nhap = nhapRepository.save(nhapExist);
//
//        return new PhieuNhapDTO().convertToDto(nhap);
//    }
}


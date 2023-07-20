package com.example.computershopserver.dto;

import com.example.computershopserver.entity.Imports;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class PhieuNhapResponseDTO {
    private Long nhapId;
    private LocalDate createDate;
    private String email;
    private String company;
    private String status;
    private List<CTPhieuNhapResponseDTO> ctpNhapResponseDTOS;

    public PhieuNhapResponseDTO convertToDto(Imports phieuNhap) {
        PhieuNhapResponseDTO nhapDTO = new PhieuNhapResponseDTO();
        nhapDTO.setNhapId(phieuNhap.getId());
        nhapDTO.setCreateDate(phieuNhap.getNgaylapphieu());
        nhapDTO.setEmail(phieuNhap.getUser().getEmail());
        //nhapDTO.setDatId(phieuNhap.getPhieuDat().getDatId())
        nhapDTO.setCompany(phieuNhap.getCompany());
        nhapDTO.setStatus(phieuNhap.getStatus());
        return nhapDTO;
    }
    public Imports convertToEti(PhieuNhapResponseDTO nhapDTO) {
        Imports nhap = new Imports();

        nhap.setNgaylapphieu(LocalDate.now());

        return nhap;
    }

    public List<PhieuNhapResponseDTO> toListDto(List<Imports> listEntity) {
        List<PhieuNhapResponseDTO> listDto = new ArrayList<>();

        if(Objects.nonNull(listEntity)) {
            listEntity.forEach(e->{
                listDto.add(this.convertToDto(e));
            });
        }
        return listDto;
    }
}

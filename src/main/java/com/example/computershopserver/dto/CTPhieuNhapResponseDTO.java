package com.example.computershopserver.dto;

import com.example.computershopserver.entity.DetailImport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class CTPhieuNhapResponseDTO {
    private Long id;
    private int qtyNhap;
    private float priceNhap;
    private String productName;
    private String imageDTOS;
    public CTPhieuNhapResponseDTO convertToDto(DetailImport detail) {
        CTPhieuNhapResponseDTO deatailDTO = new CTPhieuNhapResponseDTO();

        deatailDTO.setId(detail.getId());
        deatailDTO.setQtyNhap(detail.getQuantity());
        deatailDTO.setPriceNhap(detail.getPrice());
        deatailDTO.setImageDTOS(detail.getProduct().getImage());
        deatailDTO.setProductName(detail.getProduct().getName());
        return deatailDTO;
    }

    public DetailImport convertToEti(CTPhieuNhapResponseDTO deatailDTO) {
        DetailImport detail = new DetailImport();

        detail.setId(deatailDTO.getId());
        detail.setQuantity(deatailDTO.getQtyNhap());
        detail.setPrice(deatailDTO.getPriceNhap());
        return detail;
    }


    public List<CTPhieuNhapResponseDTO> toListDto(List<DetailImport> listEntity) {
        List<CTPhieuNhapResponseDTO> listDto = new ArrayList<>();
        if (Objects.nonNull(listEntity)) {
            listEntity.forEach(e -> {
                listDto.add(this.convertToDto(e));
            });
        }
        return listDto;
    }
}

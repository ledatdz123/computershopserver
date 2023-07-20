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
public class CTPhieuNhapDTO {
    private Long id;
    private Integer quantity;
    private Float price;
    private Long idimport;
    private Long idproduct;
    private String image;
    private String productName;
    public CTPhieuNhapDTO entityToDTO(DetailImport ctPhieuNhap) {
        CTPhieuNhapDTO dto = new CTPhieuNhapDTO();
        dto.setId(ctPhieuNhap.getId());
        dto.setQuantity(ctPhieuNhap.getQuantity());
        dto.setPrice(ctPhieuNhap.getPrice());
        dto.setIdproduct(ctPhieuNhap.getProduct().getId());
        dto.setIdimport(ctPhieuNhap.getImports().getId());
        dto.setImage(ctPhieuNhap.getProduct().getImage());
        dto.setProductName(ctPhieuNhap.getProduct().getName());
        return dto;
    }

    public DetailImport dtoToEntity(CTPhieuNhapDTO dto){
        DetailImport ctPhieuNhap = new DetailImport();
        ctPhieuNhap.setId(dto.getId());
        ctPhieuNhap.setQuantity(dto.getQuantity());
        ctPhieuNhap.setPrice(dto.getPrice());
        return ctPhieuNhap;
    }

    public List<CTPhieuNhapDTO> entityToDTO(List<DetailImport> ctPhieuNhaps) {
        List<CTPhieuNhapDTO> list = new ArrayList<>();
        if (Objects.nonNull(ctPhieuNhaps)) {
            ctPhieuNhaps.forEach(x -> list.add(entityToDTO(x)));
        }
        return list;
    }

    public List<DetailImport> dtoToEntity(List<CTPhieuNhapDTO> dtos) {
        List<DetailImport> list = new ArrayList<>();
        if (Objects.nonNull(dtos)) {
            dtos.forEach(x -> list.add(dtoToEntity(x)));
        }
        return list;
    }
}
package com.example.computershopserver.api;

import com.example.computershopserver.dto.CTPhieuNhapDTO;
import com.example.computershopserver.dto.CTPhieuNhapResponseDTO;
import com.example.computershopserver.dto.ResponseDTO;
import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.dto.responsecode.SuccessCode;
import com.example.computershopserver.exception.*;
import com.example.computershopserver.service.DetailImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dtimport")
public class DetailImportController {
    @Autowired
    private DetailImportService ctPhieuNhapService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<CTPhieuNhapDTO> nhapDTOS = ctPhieuNhapService.retrieveCTPNs();
            List list = Collections.synchronizedList(new ArrayList(nhapDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(nhapDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_CT_PHIEU_NHAP_ERROR);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/import/{import_id}")
    public ResponseEntity<ResponseDTO> findDetailByOrder(@PathVariable("import_id") @NotBlank Long orderId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        List<CTPhieuNhapResponseDTO> detailDTOS = ctPhieuNhapService.findDetailByOrder(orderId);
        responseDTO.setData(detailDTOS);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{detail_id}")
    public ResponseEntity<ResponseDTO> findOrderDetail(@PathVariable("detail_id") Long detailId) throws ResourceNotFoundException {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Optional<CTPhieuNhapDTO> detailDTO = ctPhieuNhapService.getCTPN(detailId);

            responseDTO.setData(detailDTO);
            responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        } catch (Exception e){
            throw new ResourceNotFoundException(""+ErrorCode.FIND_ORDER_DETAIL_ERROR);
        }
        return ResponseEntity.ok(responseDTO);
    }
    @PostMapping("/add")
    /*
    {
    "quantity": "2",
    "price": "800",
    "idimport": 1,
    "idproduct":1
}
     */
    public ResponseEntity<ResponseDTO> createCTPN(@RequestBody CTPhieuNhapDTO nhapDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPhieuNhapDTO dto = ctPhieuNhapService.saveCTPN(nhapDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //update
    @PutMapping("/update/{nhapId}")
    public ResponseEntity<ResponseDTO> updatePN(@PathVariable("nhapId") Long nhapId
    ) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CTPhieuNhapDTO updatePN = ctPhieuNhapService.updateCTPN(nhapId);

            responseDTO.setData(updatePN);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/delete/{nhapId}")
    public ResponseEntity<ResponseDTO> deletePN(@PathVariable("nhapId") Long nhapId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = ctPhieuNhapService.deleteCTPN(nhapId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_CT_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_CT_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
}


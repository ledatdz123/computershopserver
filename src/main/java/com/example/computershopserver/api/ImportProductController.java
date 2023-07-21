package com.example.computershopserver.api;

import com.example.computershopserver.dto.PhieuNhapDTO;
import com.example.computershopserver.dto.PhieuNhapResponseDTO;
import com.example.computershopserver.dto.ResponseDTO;
import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.dto.responsecode.SuccessCode;
import com.example.computershopserver.exception.AddDataFail;
import com.example.computershopserver.exception.DeleteDataFail;
import com.example.computershopserver.exception.GetDataFail;
import com.example.computershopserver.exception.UpdateDataFail;
import com.example.computershopserver.service.ImportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/imports")
public class ImportProductController {
    @Autowired
    private ImportsService nhapService;

    @GetMapping("")
    public ResponseEntity<ResponseDTO> getAll() throws GetDataFail {
        ResponseDTO response = new ResponseDTO();
        List<ResponseDTO> responseDTO = new ArrayList<>();
        try {
            List<PhieuNhapResponseDTO> nhapDTOS = nhapService.retrievePhieuNhaps();
            List list = Collections.synchronizedList(new ArrayList(nhapDTOS));

            if (responseDTO.addAll(list) == true) {
                response.setData(nhapDTOS);
            }
            response.setSuccessCode(SuccessCode.GET_ALL_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new GetDataFail(""+ ErrorCode.GET_PHIEU_NHAP_ERROR);
        }
        return ResponseEntity.ok(response);
    }

//    @GetMapping("/{nhap_id}")
//    public ResponseEntity<ResponseDTO> findPN(@PathVariable("nhap_id") Long nhapId) throws ResourceNotFoundException {
//        ResponseDTO responseDTO = new ResponseDTO();
//        try {
//            PhieuNhapResponseDTO nhapDTO = nhapService.getPhieuNhap(nhapId);
//
//            responseDTO.setData(nhapDTO);
//            responseDTO.setSuccessCode(SuccessCode.FIND_PHIEU_NHAP_SUCCESS);
//        } catch (Exception e){
//            throw new ResourceNotFoundException(""+ErrorCode.FIND_PHIEU_NHAP_ERROR);
//        }
//        return ResponseEntity.ok(responseDTO);
//    }

    @PostMapping("/add")
    /*
    {
    "iduser"
    }
     */
    public ResponseEntity<ResponseDTO> createPN(@RequestBody PhieuNhapDTO nhapDTO) throws AddDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            PhieuNhapDTO dto = nhapService.savePN(nhapDTO);
            responseDTO.setData(dto);
            responseDTO.setSuccessCode(SuccessCode.ADD_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new AddDataFail(""+ErrorCode.ADD_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }
    //
////    //update
    @PutMapping("/update/{nhap_id}")
    public ResponseEntity<ResponseDTO> updatePD(@PathVariable(value = "nhap_id") Long nhapId, @RequestBody PhieuNhapDTO dto) throws UpdateDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        System.out.println(dto);
        try {
            PhieuNhapDTO update = nhapService.updatePN(dto, nhapId);

            responseDTO.setData(update);
            responseDTO.setSuccessCode(SuccessCode.UPDATE_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new UpdateDataFail(""+ErrorCode.UPDATE_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

    ////    //delete
    @DeleteMapping("/delete/{nhap_id}")
    public ResponseEntity<ResponseDTO> deletePD(@PathVariable(value = "nhap_id") Long nhapId) throws DeleteDataFail {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            Boolean isDel = nhapService.deletePN(nhapId);
            responseDTO.setData(isDel);
            responseDTO.setSuccessCode(SuccessCode.DELETE_PHIEU_NHAP_SUCCESS);
        } catch (Exception e){
            throw new DeleteDataFail(""+ErrorCode.DELETE_PHIEU_NHAP_ERROR);
        }

        return ResponseEntity.ok(responseDTO);
    }

//    @PutMapping("/phieu/status/{nhap_id}")
//    public ResponseEntity<ResponseDTO> updateStatusOrder(@PathVariable(value = "nhap_id") Long nhapId,
//                                                         @RequestParam String status) throws UpdateDataFail {
//        ResponseDTO responseDTO = new ResponseDTO();
//        try {
//            PhieuNhapDTO update = nhapService.updateStatusPN(nhapId, status);
//
//            responseDTO.setData(update);
//            responseDTO.setSuccessCode(SuccessCode.UPDATE_PHIEU_NHAP_SUCCESS);
//        } catch (Exception e){
//            throw new UpdateDataFail(""+ErrorCode.UPDATE_PHIEU_NHAP_ERROR);
//        }
//
//        return ResponseEntity.ok(responseDTO);
//    }
}



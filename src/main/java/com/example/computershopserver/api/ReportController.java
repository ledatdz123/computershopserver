package com.example.computershopserver.api;

import com.example.computershopserver.dto.ResponseDTO;
import com.example.computershopserver.dto.responsecode.SuccessCode;
import com.example.computershopserver.service.impl.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;
//	@GetMapping("/date")
//	public List<Object[]> getReportDate(@RequestParam String startDate,
//										@RequestParam String endDate){
//		return reportService.getReportByDate(startDate, endDate);
//	}
//	@GetMapping("/topfiveproduct")
//	public List<Object[]> getTopFiveProduct(){
//
//		return reportService.getTopFiveProduct();
//	}

    @GetMapping("/topfiveproduct")
    public ResponseEntity<ResponseDTO> getTopFiveProduct(){
        ResponseDTO responseDTO = new ResponseDTO();
        List<Object[]> response = reportService.getTopFiveProduct();
        responseDTO.setData(response);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/date")
    public ResponseEntity<ResponseDTO> getReportByDate(@RequestParam String startDate,
                                                       @RequestParam String endDate){
        ResponseDTO responseDTO = new ResponseDTO();
        List<Object[]> response = reportService.getReportByDate(startDate, endDate);
        responseDTO.setData(response);
        responseDTO.setSuccessCode(SuccessCode.FIND_ORDER_DETAIL_SUCCESS);
        return ResponseEntity.ok(responseDTO);
    }
}


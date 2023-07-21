package com.example.computershopserver.api;

import com.example.computershopserver.response.ResponseMessageDto;
import com.example.computershopserver.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/upload")
public class CloudinaryController {
    @Autowired
    private CloudinaryService cloudDinaryService;
    @PostMapping("/cloudinary")
    public ResponseEntity<ResponseMessageDto> upLoad(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        Map<?, ?> resultMap = cloudDinaryService.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageDto(
                HttpStatus.OK, resultMap.get("url").toString(), LocalDateTime.now()));
    }
}


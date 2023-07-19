package com.example.computershopserver.dto;

import com.example.computershopserver.dto.responsecode.ErrorCode;
import com.example.computershopserver.dto.responsecode.SuccessCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private ErrorCode errorCode;
    private Object data;
    private SuccessCode successCode;
}

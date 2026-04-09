package com.example.wallet_service.exceptions;

import com.example.wallet_service.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGenericException(Exception e){
        return new ResponseEntity<>(
                new ApiResponseDTO(
                        500,
                        e.getMessage(),
                        null
                ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}

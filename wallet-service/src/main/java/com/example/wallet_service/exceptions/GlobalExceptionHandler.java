package com.example.wallet_service.exceptions;

import com.example.wallet_service.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler
    public ResponseEntity<ApiResponseDTO> handleResourceAlreadyExist(ResourceAlreadyExistException e) {
        return new ResponseEntity<>(
                new ApiResponseDTO(
                        409,
                        e.getMessage(),
                        null
                ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseDTO> handleResourceNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new ApiResponseDTO(
                        404,
                        e.getMessage(),
                        null
                ), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponseDTO> handleInsufficientBalance(InsufficientBalanceException e) {
        return new ResponseEntity<>(
                new ApiResponseDTO(
                        406,
                        e.getMessage(),
                        null
                ), HttpStatus.NOT_ACCEPTABLE
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO> handleValidationErrorsException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponseDTO.builder()
                        .status(400)
                        .message("Validation failed")
                        .data(errors)
                        .build()
        );
    }
}

package com.example.wallet_service.controller;

import com.example.wallet_service.dto.ApiResponseDTO;
import com.example.wallet_service.dto.CreateAccountRequestDTO;
import com.example.wallet_service.dto.UpdateAccountRequestDTO;
import com.example.wallet_service.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDTO.builder()
                        .status(201)
                        .message("Account created successfully")
                        .data(accountService.createAccount(
                                request.getName(),
                                request.getEmail(),
                                request.getPassword()
                        ))
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Account fetched successfully")
                        .data(accountService.getAccount(id))
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateAccount(@PathVariable Long id, @Valid @RequestBody UpdateAccountRequestDTO request) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Account updated successfully")
                        .data(accountService.updateAccount(
                                id,
                                request.getName(),
                                request.getEmail(),
                                request.getPassword()
                        ))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Account deleted successfully")
                        .data(null)
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDTO> getAllAccounts() {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Accounts fetched successfully")
                        .data(accountService.getAllAccounts())
                        .build()
        );
    }
}
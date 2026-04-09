package com.example.wallet_service.controller;

import com.example.wallet_service.dto.ApiResponseDTO;
import com.example.wallet_service.dto.TransferRequestDTO;
import com.example.wallet_service.dto.WalletRequestDTO;
import com.example.wallet_service.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponseDTO> deposit(@Valid @RequestBody WalletRequestDTO request) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Deposit successful")
                        .data(walletService.deposit(request.getAccountId(), request.getAmount()))
                        .build()
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdraw(@Valid @RequestBody WalletRequestDTO request) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Withdrawal successful")
                        .data(walletService.withdraw(request.getAccountId(), request.getAmount()))
                        .build()
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponseDTO> transfer(@Valid @RequestBody TransferRequestDTO request) {
        walletService.transfer(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Transfer successful")
                        .data(null)
                        .build()
        );
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ApiResponseDTO> getWallet(@PathVariable Long accountId) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Wallet fetched successfully")
                        .data(walletService.getWallet(accountId))
                        .build()
        );
    }
}
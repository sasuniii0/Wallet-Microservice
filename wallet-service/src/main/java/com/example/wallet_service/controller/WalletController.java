package com.example.wallet_service.controller;

import com.example.wallet_service.dto.ApiResponseDTO;
import com.example.wallet_service.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponseDTO> deposit(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Deposit successful")
                        .data(walletService.deposit(
                                Long.valueOf(request.get("accountId").toString()),
                                Double.valueOf(request.get("amount").toString())
                        ))
                        .build()
        );
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponseDTO> withdraw(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(
                ApiResponseDTO.builder()
                        .status(200)
                        .message("Withdrawal successful")
                        .data(walletService.withdraw(
                                Long.valueOf(request.get("accountId").toString()),
                                Double.valueOf(request.get("amount").toString())
                        ))
                        .build()
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponseDTO> transfer(@RequestBody Map<String, Object> request) {
        walletService.transfer(
                Long.valueOf(request.get("fromAccountId").toString()),
                Long.valueOf(request.get("toAccountId").toString()),
                Double.valueOf(request.get("amount").toString())
        );
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
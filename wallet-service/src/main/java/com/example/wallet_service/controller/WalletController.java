package com.example.wallet_service.controller;

import com.example.wallet_service.entity.Wallet;
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
    public Wallet deposit(@RequestBody Map<String, Object> request) {
        return walletService.deposit(
                Long.valueOf(request.get("accountId").toString()),
                Double.valueOf(request.get("amount").toString())
        );
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestBody Map<String, Object> request) {
        return walletService.withdraw(
                Long.valueOf(request.get("accountId").toString()),
                Double.valueOf(request.get("amount").toString())
        );
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Map<String, Object> request) {
        walletService.transfer(
                Long.valueOf(request.get("fromAccountId").toString()),
                Long.valueOf(request.get("toAccountId").toString()),
                Double.valueOf(request.get("amount").toString())
        );
        return ResponseEntity.ok("Transfer successful");
    }

    @GetMapping("/{accountId}")
    public Wallet getWallet(@PathVariable Long accountId) {
        return walletService.getWallet(accountId);
    }
}

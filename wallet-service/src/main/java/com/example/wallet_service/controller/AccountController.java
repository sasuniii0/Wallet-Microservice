package com.example.wallet_service.controller;

import com.example.wallet_service.entity.Account;
import com.example.wallet_service.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public Account createAccount(@RequestBody Map<String, String> request) {
        return accountService.createAccount(
                request.get("name"),
                request.get("email"),
                request.get("password")
        );
    }

    @GetMapping("/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Map<String, String> request) {
        return accountService.updateAccount(
                id,
                request.get("name"),
                request.get("email"),
                request.get("password")
        );
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "Account deleted successfully";
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}

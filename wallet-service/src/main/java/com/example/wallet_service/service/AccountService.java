package com.example.wallet_service.service;

import com.example.wallet_service.entity.Account;
import com.example.wallet_service.entity.Wallet;
import com.example.wallet_service.repository.AccountRepository;
import com.example.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Account createAccount(String name, String email, String password) {
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        Account account = Account.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        Wallet wallet = Wallet.builder()
                .balance(0.0)
                .account(account)
                .build();

        accountRepository.save(account);
        walletRepository.save(wallet);

        return account;
    }

    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @Transactional
    public Account updateAccount(Long id, String name, String email, String password) {
        Account account = getAccount(id);
        if (name != null) account.setName(name);
        if (email != null) account.setEmail(email);
        if (password != null) account.setPassword(passwordEncoder.encode(password));
        return accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Long id) {
        Account account = getAccount(id);
        walletRepository.findByAccount_Id(id).ifPresent(walletRepository::delete);
        accountRepository.delete(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}

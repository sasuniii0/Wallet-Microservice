package com.example.wallet_service.service;

import com.example.wallet_service.entity.Transaction;
import com.example.wallet_service.entity.Wallet;
import com.example.wallet_service.repository.AccountRepository;
import com.example.wallet_service.repository.TransactionRepository;
import com.example.wallet_service.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Wallet deposit(Long accountId, Double amount) {
        Wallet wallet = walletRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        transactionRepository.save(new Transaction(null, amount, "DEPOSIT", LocalDateTime.now(), accountId, null));
        return wallet;
    }

    @Transactional
    public Wallet withdraw(Long accountId, Double amount) {
        Wallet wallet = walletRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance() < amount) throw new RuntimeException("Insufficient balance");
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        transactionRepository.save(new Transaction(null, amount, "WITHDRAW", LocalDateTime.now(), accountId, null));
        return wallet;
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Wallet fromWallet = walletRepository.findByAccountId(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));
        Wallet toWallet = walletRepository.findByAccountId(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (fromWallet.getBalance() < amount) throw new RuntimeException("Insufficient balance");

        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        transactionRepository.save(new Transaction(null, amount, "TRANSFER", LocalDateTime.now(), fromAccountId, toAccountId));
    }
}

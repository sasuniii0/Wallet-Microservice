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
        Wallet wallet = walletRepository.findByAccount_Id(accountId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + amount);

        Transaction txn = Transaction.builder()
                .amount(amount)
                .type("DEPOSIT")
                .timestamp(LocalDateTime.now())
                .fromAccountId(null)
                .toAccountId(accountId)
                .build();

        transactionRepository.save(txn);
        return walletRepository.save(wallet);
    }

    @Transactional
    public Wallet withdraw(Long accountId, Double amount) {
        Wallet wallet = walletRepository.findByAccount_Id(accountId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        Transaction txn = Transaction.builder()
                .amount(amount)
                .type("WITHDRAW")
                .timestamp(LocalDateTime.now())
                .fromAccountId(accountId)
                .toAccountId(null)
                .build();

        transactionRepository.save(txn);
        return walletRepository.save(wallet);
    }

    @Transactional
    public String transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Wallet fromWallet = walletRepository.findByAccount_Id(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet toWallet = walletRepository.findByAccount_Id(toAccountId)
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (fromWallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance for transfer");
        }

        fromWallet.setBalance(fromWallet.getBalance() - amount);
        toWallet.setBalance(toWallet.getBalance() + amount);

        Transaction txn = Transaction.builder()
                .amount(amount)
                .type("TRANSFER")
                .timestamp(LocalDateTime.now())
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .build();

        transactionRepository.save(txn);
        walletRepository.save(fromWallet);
        walletRepository.save(toWallet);

        return "Transfer successful";
    }

    public Wallet getWallet(Long accountId) {
        return walletRepository.findByAccount_Id(accountId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }
}

package com.example.wallet_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransferRequestDTO {
    @NotNull(message = "Sender account ID is required")
    @Positive(message = "Sender account ID must be positive")
    private Long fromAccountId;

    @NotNull(message = "Receiver account ID is required")
    @Positive(message = "Receiver account ID must be positive")
    private Long toAccountId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;
}

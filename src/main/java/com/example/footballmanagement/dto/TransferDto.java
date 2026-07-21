package com.example.footballmanagement.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    private Long id;

    @NotNull(message = "Player ID cannot be null")
    private Long playerId;
    private String playerName; // For display purposes

    private Long oldTeamId;
    private String oldTeamName; // For display purposes

    private Long newTeamId;
    private String newTeamName; // For display purposes

    @NotNull(message = "Transfer date cannot be null")
    @PastOrPresent(message = "Transfer date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transferDate;

    @DecimalMin(value = "0.0", message = "Transfer fee cannot be negative")
    private BigDecimal transferFee;
}

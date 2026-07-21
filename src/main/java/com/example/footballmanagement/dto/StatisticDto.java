package com.example.footballmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto {
    private Long id;

    @NotNull(message = "Player ID cannot be null")
    private Long playerId;
    private String playerName; // For display purposes

    @NotNull(message = "Match ID cannot be null")
    private Long matchId;
    private String matchDescription; // For display purposes (e.g., HomeTeam vs AwayTeam)

    @Min(value = 0, message = "Goals cannot be negative")
    private Integer goals;

    @Min(value = 0, message = "Assists cannot be negative")
    private Integer assists;

    @Min(value = 0, message = "Yellow cards cannot be negative")
    private Integer yellowCards;

    @Min(value = 0, message = "Red cards cannot be negative")
    private Integer redCards;

    @Min(value = 0, message = "Minutes played cannot be negative")
    private Integer minutesPlayed;
}

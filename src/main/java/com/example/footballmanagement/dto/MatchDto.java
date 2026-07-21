package com.example.footballmanagement.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    private Long id;

    @NotNull(message = "Home team ID cannot be null")
    private Long homeTeamId;
    private String homeTeamName; // For display purposes

    @NotNull(message = "Away team ID cannot be null")
    private Long awayTeamId;
    private String awayTeamName; // For display purposes

    @NotNull(message = "Match date cannot be null")
    @FutureOrPresent(message = "Match date cannot be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime matchDate;

    @NotBlank(message = "Location cannot be empty")
    @Size(max = 255, message = "Location cannot exceed 255 characters")
    private String location;

    @Min(value = 0, message = "Score cannot be negative")
    private Integer homeScore;

    @Min(value = 0, message = "Score cannot be negative")
    private Integer awayScore;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status; // e.g., Scheduled, Ongoing, Finished, Postponed, Cancelled
}

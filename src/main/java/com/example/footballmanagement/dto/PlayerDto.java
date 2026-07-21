package com.example.footballmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {
    private Long id;

    @NotBlank(message = "First name cannot be empty")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @PastOrPresent(message = "Date of birth cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Size(max = 50, message = "Nationality cannot exceed 50 characters")
    private String nationality;

    @NotBlank(message = "Position cannot be empty")
    @Size(max = 50, message = "Position cannot exceed 50 characters")
    private String position; // e.g., Goalkeeper, Defender, Midfielder, Forward

    @Min(value = 1, message = "Jersey number must be at least 1")
    private Integer jerseyNumber;

    private Long teamId; // ID of the team the player belongs to
    private String teamName; // For display purposes

    @PastOrPresent(message = "Contract start date cannot be in the future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractEndDate;

    @NotBlank(message = "Status cannot be empty")
    @Size(max = 50, message = "Status cannot exceed 50 characters")
    private String status; // e.g., New, Active, Transferred, Retired
}

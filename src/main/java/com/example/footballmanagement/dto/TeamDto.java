package com.example.footballmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto {
    private Long id;

    @NotBlank(message = "Team name cannot be empty")
    @Size(min = 2, max = 100, message = "Team name must be between 2 and 100 characters")
    private String name;

    @Size(max = 100, message = "City name cannot exceed 100 characters")
    private String city;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foundedDate;

    private Long coachId; // ID of the coach assigned to the team
    private String coachFirstName; // For display purposes
    private String coachLastName; // For display purposes
}

package com.example.footballmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeStatisticsDTO {
    private long totalPlayers;
    private long totalTeams;
    private long upcomingMatches;
    private long championships;
}

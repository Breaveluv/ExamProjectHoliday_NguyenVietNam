package com.example.footballmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Long totalTeams;
    private Long totalPlayers;
    private Long totalCoaches;
    private Long totalMatches; // Thêm trường này
    private Long totalTransfers; // Thêm trường này
    private List<MatchSummary> upcomingMatches;
    private List<TopScorer> topScorers;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchSummary {
        private Long matchId;
        private String homeTeamName;
        private String awayTeamName;
        private LocalDateTime matchDate;
        private String location;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopScorer {
        private Long playerId;
        private String playerName;
        private String teamName;
        private Integer totalGoals;
    }
}

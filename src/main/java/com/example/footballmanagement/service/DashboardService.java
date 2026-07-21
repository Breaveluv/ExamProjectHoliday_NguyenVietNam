package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.DashboardResponse;
import com.example.footballmanagement.repository.CoachRepository;
import com.example.footballmanagement.repository.MatchRepository;
import com.example.footballmanagement.repository.PlayerRepository;
import com.example.footballmanagement.repository.StatisticRepository;
import com.example.footballmanagement.repository.TeamRepository;
import com.example.footballmanagement.repository.TransferRepository; // Import TransferRepository
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;
    private final MatchRepository matchRepository;
    private final StatisticRepository statisticRepository;
    private final TransferRepository transferRepository; // Inject TransferRepository

    public DashboardService(TeamRepository teamRepository,
                            PlayerRepository playerRepository,
                            CoachRepository coachRepository,
                            MatchRepository matchRepository,
                            StatisticRepository statisticRepository,
                            TransferRepository transferRepository) { // Add to constructor
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.coachRepository = coachRepository;
        this.matchRepository = matchRepository;
        this.statisticRepository = statisticRepository;
        this.transferRepository = transferRepository;
    }

    public DashboardResponse getDashboardData() {
        DashboardResponse response = new DashboardResponse();

        // Total Teams
        response.setTotalTeams(teamRepository.count());

        // Total Players
        response.setTotalPlayers(playerRepository.count());

        // Total Coaches
        response.setTotalCoaches(coachRepository.count());

        // Total Matches
        response.setTotalMatches(matchRepository.count()); // Thêm dòng này

        // Total Transfers
        response.setTotalTransfers(transferRepository.count()); // Thêm dòng này

        // Upcoming Matches (e.g., matches scheduled in the future)
        List<DashboardResponse.MatchSummary> upcomingMatches = matchRepository.findAll().stream()
                .filter(match -> match.getMatchDate().isAfter(LocalDateTime.now()))
                .map(match -> new DashboardResponse.MatchSummary(
                        match.getId(),
                        match.getHomeTeam().getName(),
                        match.getAwayTeam().getName(),
                        match.getMatchDate(),
                        match.getLocation()
                ))
                .limit(5) // Limit to 5 upcoming matches
                .collect(Collectors.toList());
        response.setUpcomingMatches(upcomingMatches);

        // Top Scorers (simplified: sum of goals from all statistics)
        // This requires a custom query or more complex logic. For now, a basic aggregation.
        List<Object[]> topScorersRaw = statisticRepository.findTopScorers();
        List<DashboardResponse.TopScorer> topScorers = topScorersRaw.stream()
                .map(obj -> new DashboardResponse.TopScorer(
                        (Long) obj[0], // player_id
                        (String) obj[1], // player_name
                        (String) obj[2], // team_name
                        ((Number) obj[3]).intValue() // total_goals
                ))
                .collect(Collectors.toList());
        response.setTopScorers(topScorers);

        return response;
    }
}

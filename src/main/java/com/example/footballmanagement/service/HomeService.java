package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.HomeStatisticsDTO;
import com.example.footballmanagement.entity.Match;
import com.example.footballmanagement.entity.Player;
import com.example.footballmanagement.entity.Team;
import com.example.footballmanagement.repository.MatchRepository;
import com.example.footballmanagement.repository.PlayerRepository;
import com.example.footballmanagement.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public List<Player> getFeaturedPlayers(int count) {
        return playerRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();
    }

    public List<Team> getTopTeams(int count) {
        return teamRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "createdAt"))).getContent();
    }

    public List<Match> getLatestMatches(int count) {
        return matchRepository.findAll(PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, "matchDate"))).getContent();
    }

    public HomeStatisticsDTO getStatistics() {
        long totalPlayers = playerRepository.count();
        long totalTeams = teamRepository.count();
        // Assuming match status 'SCHEDULED' or similar indicates upcoming matches
        // For simplicity, we just count all matches here, but ideally we query by status.
        long upcomingMatches = matchRepository.count(); 
        long championships = 5; // Placeholder
        
        return HomeStatisticsDTO.builder()
                .totalPlayers(totalPlayers)
                .totalTeams(totalTeams)
                .upcomingMatches(upcomingMatches)
                .championships(championships)
                .build();
    }
}

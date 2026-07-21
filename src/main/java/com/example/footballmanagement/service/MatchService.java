package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.MatchDto;
import com.example.footballmanagement.entity.Match;
import com.example.footballmanagement.entity.Team;
import com.example.footballmanagement.repository.MatchRepository;
import com.example.footballmanagement.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public List<MatchDto> getAllMatches() {
        return matchRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public MatchDto getMatchById(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found with id: " + id));
        return mapToDto(match);
    }

    public MatchDto createMatch(MatchDto matchDto) {
        if (matchDto.getHomeTeamId().equals(matchDto.getAwayTeamId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Home team and away team cannot be the same.");
        }

        Team homeTeam = teamRepository.findById(matchDto.getHomeTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home team not found with id: " + matchDto.getHomeTeamId()));
        Team awayTeam = teamRepository.findById(matchDto.getAwayTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Away team not found with id: " + matchDto.getAwayTeamId()));

        Match match = new Match();
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        match.setMatchDate(matchDto.getMatchDate());
        match.setLocation(matchDto.getLocation());
        match.setHomeScore(matchDto.getHomeScore());
        match.setAwayScore(matchDto.getAwayScore());
        match.setStatus(matchDto.getStatus());

        Match savedMatch = matchRepository.save(match);
        return mapToDto(savedMatch);
    }

    public MatchDto updateMatch(Long id, MatchDto matchDto) {
        Match existingMatch = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found with id: " + id));

        if (matchDto.getHomeTeamId().equals(matchDto.getAwayTeamId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Home team and away team cannot be the same.");
        }

        Team homeTeam = teamRepository.findById(matchDto.getHomeTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Home team not found with id: " + matchDto.getHomeTeamId()));
        Team awayTeam = teamRepository.findById(matchDto.getAwayTeamId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Away team not found with id: " + matchDto.getAwayTeamId()));

        existingMatch.setHomeTeam(homeTeam);
        existingMatch.setAwayTeam(awayTeam);
        existingMatch.setMatchDate(matchDto.getMatchDate());
        existingMatch.setLocation(matchDto.getLocation());
        existingMatch.setHomeScore(matchDto.getHomeScore());
        existingMatch.setAwayScore(matchDto.getAwayScore());
        existingMatch.setStatus(matchDto.getStatus());

        Match updatedMatch = matchRepository.save(existingMatch);
        return mapToDto(updatedMatch);
    }

    public void deleteMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found with id: " + id));
        matchRepository.delete(match);
    }

    private MatchDto mapToDto(Match match) {
        return new MatchDto(
                match.getId(),
                match.getHomeTeam().getId(),
                match.getHomeTeam().getName(),
                match.getAwayTeam().getId(),
                match.getAwayTeam().getName(),
                match.getMatchDate(),
                match.getLocation(),
                match.getHomeScore(),
                match.getAwayScore(),
                match.getStatus()
        );
    }
}

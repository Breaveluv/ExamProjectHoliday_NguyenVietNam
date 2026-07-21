package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.StatisticDto;
import com.example.footballmanagement.entity.Match;
import com.example.footballmanagement.entity.Player;
import com.example.footballmanagement.entity.Statistic;
import com.example.footballmanagement.repository.MatchRepository;
import com.example.footballmanagement.repository.PlayerRepository;
import com.example.footballmanagement.repository.StatisticRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    public StatisticService(StatisticRepository statisticRepository, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.statisticRepository = statisticRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public List<StatisticDto> getAllStatistics() {
        return statisticRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public StatisticDto getStatisticById(Long id) {
        Statistic statistic = statisticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Statistic not found with id: " + id));
        return mapToDto(statistic);
    }

    public StatisticDto createStatistic(StatisticDto statisticDto) {
        Player player = playerRepository.findById(statisticDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + statisticDto.getPlayerId()));
        Match match = matchRepository.findById(statisticDto.getMatchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found with id: " + statisticDto.getMatchId()));

        Statistic statistic = new Statistic();
        statistic.setPlayer(player);
        statistic.setMatch(match);
        statistic.setGoals(statisticDto.getGoals());
        statistic.setAssists(statisticDto.getAssists());
        statistic.setYellowCards(statisticDto.getYellowCards());
        statistic.setRedCards(statisticDto.getRedCards());
        statistic.setMinutesPlayed(statisticDto.getMinutesPlayed());

        Statistic savedStatistic = statisticRepository.save(statistic);
        return mapToDto(savedStatistic);
    }

    public StatisticDto updateStatistic(Long id, StatisticDto statisticDto) {
        Statistic existingStatistic = statisticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Statistic not found with id: " + id));

        Player player = playerRepository.findById(statisticDto.getPlayerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + statisticDto.getPlayerId()));
        Match match = matchRepository.findById(statisticDto.getMatchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Match not found with id: " + statisticDto.getMatchId()));

        existingStatistic.setPlayer(player);
        existingStatistic.setMatch(match);
        existingStatistic.setGoals(statisticDto.getGoals());
        existingStatistic.setAssists(statisticDto.getAssists());
        existingStatistic.setYellowCards(statisticDto.getYellowCards());
        existingStatistic.setRedCards(statisticDto.getRedCards());
        existingStatistic.setMinutesPlayed(statisticDto.getMinutesPlayed());

        Statistic updatedStatistic = statisticRepository.save(existingStatistic);
        return mapToDto(updatedStatistic);
    }

    public void deleteStatistic(Long id) {
        Statistic statistic = statisticRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Statistic not found with id: " + id));
        statisticRepository.delete(statistic);
    }

    private StatisticDto mapToDto(Statistic statistic) {
        String matchDescription = statistic.getMatch().getHomeTeam().getName() + " vs " + statistic.getMatch().getAwayTeam().getName() + " on " + statistic.getMatch().getMatchDate().toLocalDate();
        return new StatisticDto(
                statistic.getId(),
                statistic.getPlayer().getId(),
                statistic.getPlayer().getFirstName() + " " + statistic.getPlayer().getLastName(),
                statistic.getMatch().getId(),
                matchDescription,
                statistic.getGoals(),
                statistic.getAssists(),
                statistic.getYellowCards(),
                statistic.getRedCards(),
                statistic.getMinutesPlayed()
        );
    }
}

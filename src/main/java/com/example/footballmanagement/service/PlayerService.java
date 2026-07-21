package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.PlayerDto;
import com.example.footballmanagement.entity.Player;
import com.example.footballmanagement.entity.Team;
import com.example.footballmanagement.repository.PlayerRepository;
import com.example.footballmanagement.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public PlayerDto getPlayerById(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + id));
        return mapToDto(player);
    }

    public PlayerDto createPlayer(PlayerDto playerDto) {
        Player player = new Player();
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        player.setDateOfBirth(playerDto.getDateOfBirth());
        player.setNationality(playerDto.getNationality());
        player.setPosition(playerDto.getPosition());
        player.setJerseyNumber(playerDto.getJerseyNumber());
        player.setContractStartDate(playerDto.getContractStartDate());
        player.setContractEndDate(playerDto.getContractEndDate());
        player.setStatus(playerDto.getStatus());

        if (playerDto.getTeamId() != null) {
            Team team = teamRepository.findById(playerDto.getTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + playerDto.getTeamId()));
            player.setTeam(team);
        }

        Player savedPlayer = playerRepository.save(player);
        return mapToDto(savedPlayer);
    }

    public PlayerDto updatePlayer(Long id, PlayerDto playerDto) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + id));

        existingPlayer.setFirstName(playerDto.getFirstName());
        existingPlayer.setLastName(playerDto.getLastName());
        existingPlayer.setDateOfBirth(playerDto.getDateOfBirth());
        existingPlayer.setNationality(playerDto.getNationality());
        existingPlayer.setPosition(playerDto.getPosition());
        existingPlayer.setJerseyNumber(playerDto.getJerseyNumber());
        existingPlayer.setContractStartDate(playerDto.getContractStartDate());
        existingPlayer.setContractEndDate(playerDto.getContractEndDate());
        existingPlayer.setStatus(playerDto.getStatus());

        if (playerDto.getTeamId() != null) {
            Team team = teamRepository.findById(playerDto.getTeamId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + playerDto.getTeamId()));
            existingPlayer.setTeam(team);
        } else {
            existingPlayer.setTeam(null); // Disassociate player from team
        }

        Player updatedPlayer = playerRepository.save(existingPlayer);
        return mapToDto(updatedPlayer);
    }

    public void deletePlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + id));
        playerRepository.delete(player);
    }

    private PlayerDto mapToDto(Player player) {
        Long teamId = Optional.ofNullable(player.getTeam()).map(Team::getId).orElse(null);
        String teamName = Optional.ofNullable(player.getTeam()).map(Team::getName).orElse(null);
        return new PlayerDto(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getDateOfBirth(),
                player.getNationality(),
                player.getPosition(),
                player.getJerseyNumber(),
                teamId,
                teamName,
                player.getContractStartDate(),
                player.getContractEndDate(),
                player.getStatus()
        );
    }
}

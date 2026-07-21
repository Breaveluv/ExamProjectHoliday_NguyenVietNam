package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.TeamDto;
import com.example.footballmanagement.entity.Coach;
import com.example.footballmanagement.entity.Team;
import com.example.footballmanagement.repository.CoachRepository;
import com.example.footballmanagement.repository.TeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final CoachRepository coachRepository;

    public TeamService(TeamRepository teamRepository, CoachRepository coachRepository) {
        this.teamRepository = teamRepository;
        this.coachRepository = coachRepository;
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TeamDto getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + id));
        return mapToDto(team);
    }

    public TeamDto createTeam(TeamDto teamDto) {
        if (teamRepository.findByName(teamDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team with name " + teamDto.getName() + " already exists.");
        }

        Team team = new Team();
        team.setName(teamDto.getName());
        team.setCity(teamDto.getCity());
        team.setFoundedDate(teamDto.getFoundedDate());

        if (teamDto.getCoachId() != null) {
            Coach coach = coachRepository.findById(teamDto.getCoachId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found with id: " + teamDto.getCoachId()));
            team.setCoach(coach);
        }

        Team savedTeam = teamRepository.save(team);
        return mapToDto(savedTeam);
    }

    public TeamDto updateTeam(Long id, TeamDto teamDto) {
        Team existingTeam = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + id));

        if (!existingTeam.getName().equals(teamDto.getName()) && teamRepository.findByName(teamDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team with name " + teamDto.getName() + " already exists.");
        }

        existingTeam.setName(teamDto.getName());
        existingTeam.setCity(teamDto.getCity());
        existingTeam.setFoundedDate(teamDto.getFoundedDate());

        if (teamDto.getCoachId() != null) {
            Coach coach = coachRepository.findById(teamDto.getCoachId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found with id: " + teamDto.getCoachId()));
            existingTeam.setCoach(coach);
        } else {
            existingTeam.setCoach(null); // Disassociate coach
        }

        Team updatedTeam = teamRepository.save(existingTeam);
        return mapToDto(updatedTeam);
    }

    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found with id: " + id));
        teamRepository.delete(team);
    }

    private TeamDto mapToDto(Team team) {
        Long coachId = Optional.ofNullable(team.getCoach()).map(Coach::getId).orElse(null);
        String coachFirstName = Optional.ofNullable(team.getCoach()).map(Coach::getFirstName).orElse(null);
        String coachLastName = Optional.ofNullable(team.getCoach()).map(Coach::getLastName).orElse(null);
        return new TeamDto(team.getId(), team.getName(), team.getCity(), team.getFoundedDate(), coachId, coachFirstName, coachLastName);
    }
}

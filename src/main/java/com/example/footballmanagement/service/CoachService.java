package com.example.footballmanagement.service;

import com.example.footballmanagement.dto.CoachDto;
import com.example.footballmanagement.entity.Coach;
import com.example.footballmanagement.repository.CoachRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public List<CoachDto> getAllCoaches() {
        return coachRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CoachDto getCoachById(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found with id: " + id));
        return mapToDto(coach);
    }

    public CoachDto createCoach(CoachDto coachDto) {
        Coach coach = new Coach();
        coach.setFirstName(coachDto.getFirstName());
        coach.setLastName(coachDto.getLastName());
        coach.setDateOfBirth(coachDto.getDateOfBirth());
        coach.setNationality(coachDto.getNationality());

        Coach savedCoach = coachRepository.save(coach);
        return mapToDto(savedCoach);
    }

    public CoachDto updateCoach(Long id, CoachDto coachDto) {
        Coach existingCoach = coachRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found with id: " + id));

        existingCoach.setFirstName(coachDto.getFirstName());
        existingCoach.setLastName(coachDto.getLastName());
        existingCoach.setDateOfBirth(coachDto.getDateOfBirth());
        existingCoach.setNationality(coachDto.getNationality());

        Coach updatedCoach = coachRepository.save(existingCoach);
        return mapToDto(updatedCoach);
    }

    public void deleteCoach(Long id) {
        Coach coach = coachRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Coach not found with id: " + id));
        // Before deleting a coach, consider if any team is associated with this coach.
        // The foreign key in 'teams' table has ON DELETE SET NULL, so it should be fine.
        coachRepository.delete(coach);
    }

    private CoachDto mapToDto(Coach coach) {
        return new CoachDto(
                coach.getId(),
                coach.getFirstName(),
                coach.getLastName(),
                coach.getDateOfBirth(),
                coach.getNationality()
        );
    }
}

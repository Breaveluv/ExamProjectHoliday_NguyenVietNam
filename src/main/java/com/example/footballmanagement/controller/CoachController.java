package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.CoachDto;
import com.example.footballmanagement.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CoachDto>>> getAllCoaches() {
        List<CoachDto> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(ApiResponse.success("Coaches retrieved successfully", coaches));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CoachDto>> getCoachById(@PathVariable Long id) {
        CoachDto coach = coachService.getCoachById(id);
        return ResponseEntity.ok(ApiResponse.success("Coach retrieved successfully", coach));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<CoachDto>> createCoach(@Valid @RequestBody CoachDto coachDto) {
        CoachDto createdCoach = coachService.createCoach(coachDto);
        return new ResponseEntity<>(ApiResponse.success("Coach created successfully", createdCoach), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CoachDto>> updateCoach(@PathVariable Long id, @Valid @RequestBody CoachDto coachDto) {
        CoachDto updatedCoach = coachService.updateCoach(id, coachDto);
        return ResponseEntity.ok(ApiResponse.success("Coach updated successfully", updatedCoach));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCoach(@PathVariable Long id) {
        coachService.deleteCoach(id);
        return ResponseEntity.ok(ApiResponse.success("Coach deleted successfully", null));
    }
}

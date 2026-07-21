package com.example.footballmanagement.controller;

import com.example.footballmanagement.dto.ApiResponse;
import com.example.footballmanagement.dto.StatisticDto;
import com.example.footballmanagement.service.StatisticService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<StatisticDto>>> getAllStatistics() {
        List<StatisticDto> statistics = statisticService.getAllStatistics();
        return ResponseEntity.ok(ApiResponse.success("Statistics retrieved successfully", statistics));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StatisticDto>> getStatisticById(@PathVariable Long id) {
        StatisticDto statistic = statisticService.getStatisticById(id);
        return ResponseEntity.ok(ApiResponse.success("Statistic retrieved successfully", statistic));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<StatisticDto>> createStatistic(@Valid @RequestBody StatisticDto statisticDto) {
        StatisticDto createdStatistic = statisticService.createStatistic(statisticDto);
        return new ResponseEntity<>(ApiResponse.success("Statistic created successfully", createdStatistic), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StatisticDto>> updateStatistic(@PathVariable Long id, @Valid @RequestBody StatisticDto statisticDto) {
        StatisticDto updatedStatistic = statisticService.updateStatistic(id, statisticDto);
        return ResponseEntity.ok(ApiResponse.success("Statistic updated successfully", updatedStatistic));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStatistic(@PathVariable Long id) {
        statisticService.deleteStatistic(id);
        return ResponseEntity.ok(ApiResponse.success("Statistic deleted successfully", null));
    }
}

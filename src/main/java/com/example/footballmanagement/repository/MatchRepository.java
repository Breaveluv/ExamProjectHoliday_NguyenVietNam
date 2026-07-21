package com.example.footballmanagement.repository;

import com.example.footballmanagement.entity.Match;
import com.example.footballmanagement.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findByHomeTeamAndAwayTeamAndMatchDate(Team homeTeam, Team awayTeam, LocalDateTime matchDate);
}

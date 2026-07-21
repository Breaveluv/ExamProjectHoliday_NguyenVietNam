package com.example.footballmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"player", "match"}) // Exclude related entities to prevent StackOverflowError
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", nullable = false)
    @NotNull(message = "Player cannot be empty")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    @NotNull(message = "Match cannot be empty")
    private Match match;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer goals;

    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer assists;

    @Column(name = "yellow_cards", columnDefinition = "INT DEFAULT 0")
    private Integer yellowCards;

    @Column(name = "red_cards", columnDefinition = "INT DEFAULT 0")
    private Integer redCards;

    @Column(name = "minutes_played", columnDefinition = "INT DEFAULT 0")
    private Integer minutesPlayed;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

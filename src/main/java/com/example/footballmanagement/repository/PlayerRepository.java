package com.example.footballmanagement.repository;

import com.example.footballmanagement.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByFirstNameAndLastName(String firstName, String lastName);
}

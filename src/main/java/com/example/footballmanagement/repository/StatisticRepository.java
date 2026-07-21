package com.example.footballmanagement.repository;

import com.example.footballmanagement.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    @Query("SELECT p.id, CONCAT(p.firstName, ' ', p.lastName), t.name, SUM(s.goals) " +
           "FROM Statistic s JOIN s.player p JOIN p.team t " +
           "GROUP BY p.id, p.firstName, p.lastName, t.name " +
           "ORDER BY SUM(s.goals) DESC")
    List<Object[]> findTopScorers();
}

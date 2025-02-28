package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByRoomId(Long roomId);
}
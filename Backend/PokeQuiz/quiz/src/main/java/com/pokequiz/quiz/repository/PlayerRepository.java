package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    List<Player> findByQuizRoomId(String quizRoomId); // Fetch players by room ID

    Player findByUserIdAndQuizRoomId(Long userId, String quizRoomId); // Find a specific player by user and room
}

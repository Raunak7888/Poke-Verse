package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.QuizRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRoundRepository extends JpaRepository<QuizRound, Long> {

    List<QuizRound> findByQuizRoomId(String quizRoomId); // Fetch all rounds by room ID

    QuizRound findTopByQuizRoomIdOrderByRoundNumberDesc(String quizRoomId); // Get the latest round
}

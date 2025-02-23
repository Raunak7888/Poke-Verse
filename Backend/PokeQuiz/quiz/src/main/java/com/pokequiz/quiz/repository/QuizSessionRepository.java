package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.QuizSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizSessionRepository extends JpaRepository<QuizSession, Long> {

    List<QuizSession> findByUserId(String userId);

    QuizSession findByUserIdAndStatus(String userId, QuizSession.SessionStatus status);


    QuizSession findBySessionIdAndStatus(Long sessionId, QuizSession.SessionStatus status);


}

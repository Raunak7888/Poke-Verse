package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.QuizAttempt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO quiz_attempts_partition_:partitionId 
        (userId, quizId, questionId, selectedAnswer, isCorrect, createdAt) 
        VALUES (:userId, :quizId, :questionId, :selectedAnswer, :isCorrect, :createdAt)
    """, nativeQuery = true)
    void insertIntoPartition(
            @Param("partitionId") int partitionId,
            @Param("userId") Long userId,
            @Param("quizId") Long quizId,
            @Param("questionId") Long questionId,
            @Param("selectedAnswer") String selectedAnswer,
            @Param("isCorrect") boolean isCorrect,
            @Param("createdAt") LocalDateTime createdAt
    );

    @Query("SELECT q FROM QuizAttempt q WHERE q.userId = :userId ORDER BY q.createdAt DESC")
    List<QuizAttempt> findByUserId(@Param("userId") Long userId);
}

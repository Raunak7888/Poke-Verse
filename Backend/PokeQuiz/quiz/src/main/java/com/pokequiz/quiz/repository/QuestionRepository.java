package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM questions WHERE region = :region AND difficulty = :difficulty ORDER BY RANDOM() LIMIT :limit")
    List<Question> findRandomQuestionsAsPerDifficultyAndRegion(
            @Param("region") String region,
            @Param("difficulty") String difficulty,
            @Param("limit") int limit
    );

    @Query(nativeQuery = true, value = "SELECT * FROM questions WHERE region = :region AND difficulty = :difficulty")
    List<Question> findByDifficultyAndRegion(
            @Param("region") String region,
            @Param("difficulty") String difficulty
    );


    @Query(nativeQuery = true, value = "SELECT * FROM questions ORDER BY RANDOM() LIMIT :limit")
    List<Question> findRandomQuestions(@Param("limit") int limit);

    List<Question> findByDifficulty(String difficulty);

    List<Question> findByRegion(String region);
}
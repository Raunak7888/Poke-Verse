package com.pokequiz.quiz.repository;

import com.pokequiz.quiz.model.QuizRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRoomRepository extends JpaRepository<QuizRoom, String> {

    List<QuizRoom> findByIsPublicTrue(); // Fetch all public quiz rooms

    Optional<QuizRoom> findByIdAndIsStartedFalse(String id); // Find open room by ID
}

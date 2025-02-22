package com.pokequiz.quiz.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuizAttemptDTO {
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long questionId;
    @Column(nullable = false)
    private String selectedAnswer;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;
}

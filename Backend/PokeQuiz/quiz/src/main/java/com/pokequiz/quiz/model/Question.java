package com.pokequiz.quiz.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "questions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String difficulty;
    private String region;

    @Column(name = "quiz_type")
    private String quizType;

    @Column(columnDefinition = "TEXT")
    private String options;  // Store as JSON text

    private String correctAnswer;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public List<String> getOptionsList() {
        return Arrays.asList(options.replace("[", "").replace("]", "").split(","));
    }

    public void setOptionsList(List<String> optionsList) {
        this.options = optionsList.toString();
    }
}

package com.pokequiz.quiz.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz_rounds")
public class QuizRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_room_id", nullable = false)
    private QuizRoom quizRoom;

    private int roundNumber;

    private boolean isCompleted = false;

    private LocalDateTime startedAt = LocalDateTime.now();

    @ElementCollection
    @CollectionTable(name = "player_answers", joinColumns = @JoinColumn(name = "round_id"))
    @MapKeyColumn(name = "user_id")
    @Column(name = "answer")
    private Map<Long, String> playerAnswers = new HashMap<>();

    public QuizRound(QuizRoom quizRoom, int roundNumber) {
        this.quizRoom = quizRoom;
        this.roundNumber = roundNumber;
    }
}

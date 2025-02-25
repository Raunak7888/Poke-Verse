package com.pokequiz.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    private int score = 0; // Default value

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIgnore
    private QuizRoom quizRoom;

    public Player(Long userId, String username, QuizRoom quizRoom) {
        this.userId = userId;
        this.username = username;
        this.quizRoom = quizRoom;
    }
}

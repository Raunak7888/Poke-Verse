package com.pokequiz.quiz.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz_rooms")
public class QuizRoom {

    @Id
    private String id;

    @NotNull
    private String name;

    private boolean isPublic;

    @NotNull
    private Long hostId;

    private int maxPlayers;

    private boolean isStarted = false;

    @OneToMany(mappedBy = "quizRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Player> players = new HashSet<>();

    @OneToMany(mappedBy = "quizRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizRound> rounds = new ArrayList<>();

    public QuizRoom(String name, boolean isPublic, int maxPlayers, Long hostId) {
        this.id = generateRoomId();
        this.name = name;
        this.isPublic = isPublic;
        this.maxPlayers = maxPlayers;
        this.hostId = hostId;
    }

    public QuizRoom(@NotNull(message = "Room name is required") String name, boolean Public, @Min(value = 2, message = "Minimum 2 players required") int maxPlayers) {
        this.name = name;
        this.isPublic = Public;
        this.maxPlayers = maxPlayers;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void addRound(QuizRound round) {
        this.rounds.add(round);
    }

    public QuizRound getCurrentRound() {
        return rounds.isEmpty() ? null : rounds.getLast();
    }

    public boolean hasOngoingRound() {
        QuizRound currentRound = getCurrentRound();
        return currentRound != null && !currentRound.isCompleted();
    }

    public int getRoundCount() {
        return rounds.size();
    }

    private String generateRoomId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder idBuilder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            idBuilder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return idBuilder.toString();
    }
}

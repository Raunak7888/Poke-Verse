package com.pokequiz.quiz.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizRoomDTO {

    @NotNull(message = "Room name is required")
    private String name;

    private boolean isPublic;

    @Min(value = 2, message = "Minimum 2 players required")
    private int maxPlayers;

    @NotNull(message = "HostId is required")
    private Long hostId;

    @Min(value = 1, message = "At least 1 round is required")
    private int noOfRounds;
}

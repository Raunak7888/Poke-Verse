package com.pokequiz.quiz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketQuizMessageDTO {

    @NotNull(message = "Message type is required")
    private String type; // e.g., "JOIN", "ANSWER", "RESULT"

    @NotNull(message = "Room ID is required")
    private String roomId;

    private Long userId; // Optional for system messages

    private String username; // Optional for anonymous or system messages

    private String content; // Message body (e.g., answer, status update)
}

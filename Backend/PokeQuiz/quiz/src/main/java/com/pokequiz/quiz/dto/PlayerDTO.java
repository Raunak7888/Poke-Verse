package com.pokequiz.quiz.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "username is required")
    private String username;
}

package com.pokequiz.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class QuestionDTO {
    @NotNull(message = "Question cannot be null")
    @JsonProperty("question")
    private String question;

    @NotNull(message = "difficulty cannot be null")
    @JsonProperty("difficulty")
    private String difficulty;

    @NotNull(message = "region cannot be null")
    @JsonProperty("region")
    private String region;

    @NotNull(message = "quizType cannot be null")
    @JsonProperty("quizType")
    private String quizType;

    @NotNull(message = "options cannot be null")
    @JsonProperty("options")
    private List<String> options;

    @NotNull(message = "correctAnswer cannot be null")
    @JsonProperty("correctAnswer")
    private String correctAnswer;


}

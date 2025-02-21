package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.dto.QuestionDTO;
import com.pokequiz.quiz.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuestionService questionService;

    public QuizController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuiz(@RequestBody QuestionDTO questionDTO) {
        if (questionDTO == null) {
            return ResponseEntity.badRequest().body("Question body cannot be null");
        }
        if(questionDTO.getCorrectAnswer() == null) {
            return ResponseEntity.badRequest().body("Correct answer cannot be null");
        }
        if (questionDTO.getOptions() == null || questionDTO.getOptions().size() != 4) {
            return ResponseEntity.badRequest().body("Options should be exactly 4.");
        }

        questionService.addQuiz(questionDTO);
        return ResponseEntity.ok("Quiz added successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(questionService.getAllQuizzes());
    }

    @GetMapping("/difficulty/all")
    public ResponseEntity<?> getAllQuizzesByDifficulty(@RequestParam String difficulty) {
        return ResponseEntity.ok(questionService.getQuizzesByDifficulty(difficulty));
    }

    @GetMapping("/region/all")
    public ResponseEntity<?> getQuizzesByRegion(@RequestParam String region) {
        return ResponseEntity.ok(questionService.getQuizzesByRegion(region));
    }

    @GetMapping("/region/difficulty/all")
    public ResponseEntity<?> getQuizzesByRegionAndDifficulty(
            @RequestParam String region,
            @RequestParam String difficulty) {
        return ResponseEntity.ok(questionService.getQuizzesByRegionAndDifficulty(region, difficulty));
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuizzes(@RequestParam int limit) {
        return ResponseEntity.ok(questionService.getRandomQuizzes(limit));
    }

    @GetMapping("/random/difficulty/region")
    public ResponseEntity<?> getRandomQuizzesAsPerDifficultyAndRegion(
            @RequestParam String region,
            @RequestParam String difficulty,
            @RequestParam int limit) {
        return ResponseEntity.ok(questionService.getRandomQuizzesAsPerDifficultyAndRegion(region, difficulty, limit));
    }
}
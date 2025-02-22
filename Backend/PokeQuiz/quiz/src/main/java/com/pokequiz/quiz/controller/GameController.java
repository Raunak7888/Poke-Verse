package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz/game")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello() {
        return ResponseEntity.ok("{\"response\": \"Hello World\"}"); // Correct JSON formatting
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllQuizzes() {
        return ResponseEntity.ok(gameService.getAllQuizzes());
    }

    @GetMapping("/difficulty/all")
    public ResponseEntity<?> getAllQuizzesByDifficulty(@RequestParam String difficulty) {
        return ResponseEntity.ok(gameService.getQuizzesByDifficulty(difficulty));
    }

    @GetMapping("/region/all")
    public ResponseEntity<?> getQuizzesByRegion(@RequestParam String region) {
        return ResponseEntity.ok(gameService.getQuizzesByRegion(region));
    }

    @GetMapping("/quiztype/all")
    public ResponseEntity<?> getQuizzesByQuizType(@RequestParam String quizType) {
        return ResponseEntity.ok(gameService.getQuizzesByQuizType(quizType));
    }

    @GetMapping("/region/difficulty/quiztype/all")
    public ResponseEntity<?> getQuizzesByRegionAndDifficulty(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String quizType) {
        return ResponseEntity.ok(gameService.getQuizzesByRegionAndDifficulty(region, difficulty, quizType));
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuizzes(@RequestParam int limit) {
        return ResponseEntity.ok(gameService.getRandomQuizzes(limit));
    }

    @GetMapping("/random/difficulty/region/quiztype")
    public ResponseEntity<?> getRandomQuizzesAsPerDifficultyAndRegion(
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String quizType,
            @RequestParam int limit) {
        return ResponseEntity.ok(gameService.getRandomQuizzesAsPerDifficultyAndRegion(region, difficulty, quizType, limit));
    }
}

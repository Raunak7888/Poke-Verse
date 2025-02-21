package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
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

    @GetMapping("/region/difficulty/all")
    public ResponseEntity<?> getQuizzesByRegionAndDifficulty(
            @RequestParam String region,
            @RequestParam String difficulty) {
        return ResponseEntity.ok(gameService.getQuizzesByRegionAndDifficulty(region, difficulty));
    }

    @GetMapping("/random")
    public ResponseEntity<?> getRandomQuizzes(@RequestParam int limit) {
        return ResponseEntity.ok(gameService.getRandomQuizzes(limit));
    }

    @GetMapping("/random/difficulty/region")
    public ResponseEntity<?> getRandomQuizzesAsPerDifficultyAndRegion(
            @RequestParam String region,
            @RequestParam String difficulty,
            @RequestParam int limit) {
        return ResponseEntity.ok(gameService.getRandomQuizzesAsPerDifficultyAndRegion(region, difficulty, limit));
    }
}

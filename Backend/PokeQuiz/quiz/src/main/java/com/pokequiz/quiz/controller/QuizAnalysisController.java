package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.model.QuizAnalysis;
import com.pokequiz.quiz.service.QuizAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-analysis")
@RequiredArgsConstructor
public class QuizAnalysisController {

    private final QuizAnalysisService quizAnalysisService;

    @PostMapping("/{sessionId}")
    public ResponseEntity<QuizAnalysis> analyzeQuiz(@PathVariable Long sessionId) {
        return ResponseEntity.ok(quizAnalysisService.analyzeQuiz(sessionId));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<QuizAnalysis> getAnalysis(@PathVariable Long sessionId) {
        return quizAnalysisService.getAnalysisBySessionId(sessionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizAnalysis>> getUserAnalyses(@PathVariable String userId) {
        return ResponseEntity.ok(quizAnalysisService.getUserAnalyses(userId));
    }


}

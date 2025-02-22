package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.dto.QuizAttemptDTO;
import com.pokequiz.quiz.model.QuizAttempt;
import com.pokequiz.quiz.service.QuizAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attempts")
public class QuizAttemptController {

    private final QuizAttemptService quizAttemptService;

    public QuizAttemptController(QuizAttemptService quizAttemptService) {
        this.quizAttemptService = quizAttemptService;
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizAttempt> submitQuizAttempt(@RequestBody QuizAttemptDTO dto) {
        if(dto.getUserId() == null || dto.getQuestionId() == null || dto.getSelectedAnswer() == null || dto.getStartTime() == null || dto.getEndTime() == null) {
            return ResponseEntity.badRequest().build();
        }
        if(dto.getStartTime().isAfter(dto.getEndTime())) {
            return ResponseEntity.badRequest().build();
        }
        QuizAttempt result = quizAttemptService.evaluateQuizAttempt(dto);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/userid")
    public ResponseEntity<List<QuizAttempt>> getUserAttempts(@RequestParam Long userId) {
        List<QuizAttempt> attempts = quizAttemptService.getUserAttempts(userId);
        return ResponseEntity.ok(attempts);
    }

    @GetMapping("/userid/time")
    public ResponseEntity<List<QuizAttempt>> getUserAttemptByTime(@RequestParam Long userId,
                                                                  @RequestParam LocalDateTime startTime,
                                                                  @RequestParam LocalDateTime endTime) {
        List<QuizAttempt> attempts = quizAttemptService.getUserAttemptByTime(userId, startTime, endTime);
        return ResponseEntity.ok(attempts);
    }


}

package com.pokequiz.quiz.service;

import com.pokequiz.quiz.model.QuizAttempt;
import com.pokequiz.quiz.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizAttemptService {

    @Autowired
    private final QuizAttemptRepository quizAttemptRepository;
    private static final int PARTITION_COUNT = 10;  // Assuming 10 partitions

    public QuizAttemptService(QuizAttemptRepository quizAttemptRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
    }

    public void saveQuizAttempt(Long userId, Long quizId, Long questionId, String selectedAnswer, String correctAnswer) {
        boolean isCorrect = selectedAnswer.equalsIgnoreCase(correctAnswer);

        int partitionId = (int) (userId % PARTITION_COUNT); // 🔥 Partition based on `userId`

        quizAttemptRepository.insertIntoPartition(partitionId, userId, quizId, questionId, selectedAnswer, isCorrect, LocalDateTime.now());
    }

    public List<QuizAttempt> getUserAttempts(Long userId) {
        return quizAttemptRepository.findByUserId(userId);
    }
}

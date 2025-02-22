package com.pokequiz.quiz.service;

import com.pokequiz.quiz.dto.QuizAttemptDTO;
import com.pokequiz.quiz.model.Answer;
import com.pokequiz.quiz.model.Question;
import com.pokequiz.quiz.model.QuizAttempt;
import com.pokequiz.quiz.repository.AnswerRepository;
import com.pokequiz.quiz.repository.QuestionRepository;
import com.pokequiz.quiz.repository.QuizAttemptRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class QuizAttemptService {

    private final QuizAttemptRepository quizAttemptRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private static final int PARTITION_COUNT = 2;  // Adjust as needed

    public QuizAttemptService(QuizAttemptRepository quizAttemptRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.quizAttemptRepository = quizAttemptRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void saveQuizAttempt(Long userId, Long questionId, String selectedAnswer, boolean isCorrect,
                                LocalDateTime startTime, LocalDateTime endTime) {
        String sql = """
            INSERT INTO quiz_attempts 
            (user_id, question_id, selected_answer, is_correct, start_time, end_time, created_at) 
            VALUES (:userId, :questionId, :selectedAnswer, :isCorrect, :startTime, :endTime, NOW())
        """;

        entityManager.createNativeQuery(sql)
                .setParameter("userId", userId)
                .setParameter("questionId", questionId)
                .setParameter("selectedAnswer", selectedAnswer)
                .setParameter("isCorrect", isCorrect)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime)
                .executeUpdate();


    }


    public List<QuizAttempt> getUserAttempts(Long userId) {
        return quizAttemptRepository.findByUserId(userId);
    }


    public QuizAttempt evaluateQuizAttempt(QuizAttemptDTO dto) {
        Optional<Question> existingQuestion = questionRepository.findById(dto.getQuestionId());
        boolean isCorrect = false;

        if (existingQuestion.isPresent()) {
            Answer existingAnswer = answerRepository.findByQuestion(existingQuestion.get());
            if (existingAnswer != null && Objects.equals(existingAnswer.getCorrectAnswer(), dto.getSelectedAnswer())) {
                isCorrect = true;
            }
        }
        QuizAttempt isAlreadyPresent = quizAttemptRepository.findByUserIdAndStartTime(dto.getUserId(), dto.getStartTime());
        if (isAlreadyPresent != null){
            return isAlreadyPresent;
        }
        // 🔥 Fixed: `answer` is now a boolean instead of a string
        saveQuizAttempt(dto.getUserId(), dto.getQuestionId(), dto.getSelectedAnswer(), isCorrect, dto.getStartTime(), dto.getEndTime());
        return quizAttemptRepository.findByUserIdAndStartTime(dto.getUserId(), dto.getStartTime());
    }


    public List<QuizAttempt> getUserAttemptByTime(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return quizAttemptRepository.findByUserIdAndStartTimeBetween(userId, startTime, endTime);
    }

    public List<QuizAttempt> getAttemptsByQuestionId(Long questionId) {
        return quizAttemptRepository.findByQuestionId(questionId);
    }
}

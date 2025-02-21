package com.pokequiz.quiz.service;

import com.pokequiz.quiz.model.Question;
import com.pokequiz.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final QuestionRepository questionRepository;

    public GameService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuizzes() {
        return questionRepository.findAll();
    }

    public List<Question> getQuizzesByDifficulty(String difficulty) {
        return questionRepository.findByDifficulty(difficulty);
    }

    public List<Question> getQuizzesByRegion(String region) {
        return questionRepository.findByRegion(region);
    }

    public List<Question> getQuizzesByRegionAndDifficulty(String region, String difficulty) {
        return questionRepository.findByDifficultyAndRegion(region, difficulty);
    }

    public List<Question> getRandomQuizzes(int limit) {
        return questionRepository.findRandomQuestions(limit);
    }

    public List<Question> getRandomQuizzesAsPerDifficultyAndRegion(String region, String difficulty, int limit) {
        return questionRepository.findRandomQuestionsAsPerDifficultyAndRegion(region, difficulty, limit);
    }


}

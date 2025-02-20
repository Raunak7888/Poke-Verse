package com.pokequiz.quiz.service;

import com.pokequiz.quiz.dto.QuestionDTO;
import com.pokequiz.quiz.model.Question;
import com.pokequiz.quiz.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuiz(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestion(questionDTO.getQuestion());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setRegion(questionDTO.getRegion());
        question.setQuizType(questionDTO.getQuizType());
        question.setOptionsList(questionDTO.getOptions());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        questionRepository.save(question);
    }

    public List<Question> getAllQuizzes() {
        List<Question> questions = questionRepository.findAll();
        questions.remove("options");
        questions.remove("correctAnswer");
        return questions;
    }

    public List<Question> getQuizzesByDifficulty(String difficulty) {
        List<Question> questions = questionRepository.findByDifficulty(difficulty);
        return questions;
    }

    public List<Question> getQuizzesByRegion(String region) {
        List<Question> questions = questionRepository.findByRegion(region);
        return questions;
    }

    public List<Question> getQuizzesByRegionAndDifficulty(String region,String difficulty) {
        List<Question> questions = questionRepository.findByDifficultyAndRegion(region, difficulty);
        return questions;
    }

    public List<Question> getRandomQuizzes(int limit) {
        List<Question> questions = questionRepository.findRandomQuestions(limit);
        return questions;
    }

    public List<Question> getRandomQuizzesAsPerDifficultyAndRegion(String region, String difficulty, int limit) {
        List<Question> questions = questionRepository.findRandomQuestionsAsPerDifficultyAndRegion(region, difficulty, limit);
        return questions;
    }
}

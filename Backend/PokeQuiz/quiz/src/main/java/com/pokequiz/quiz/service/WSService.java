package com.pokequiz.quiz.service;

import com.pokequiz.quiz.dto.WebSocketQuizMessageDTO;
import com.pokequiz.quiz.model.Question;
import com.pokequiz.quiz.model.QuizRoom;
import com.pokequiz.quiz.repository.QuizRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class WSService {

    private final QuizRoomRepository quizRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final GameService gameService;
    private final AtomicInteger questionIndex = new AtomicInteger(0);

    public WSService(QuizRoomRepository quizRoomRepository,
                     SimpMessagingTemplate messagingTemplate,
                     GameService gameService) {
        this.quizRoomRepository = quizRoomRepository;
        this.messagingTemplate = messagingTemplate;
        this.gameService = gameService;
    }

    // ✅ Handle chat messages
    public void sendMessage(String roomId, WebSocketQuizMessageDTO message) {
        quizRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!"chat".equalsIgnoreCase(message.getType())) {
            throw new IllegalArgumentException("Invalid message type");
        }

        Map<String, Object> content = Map.of(
                "type", "CHAT",
                "username", message.getUsername(),
                "message", message.getContent()
        );

        messagingTemplate.convertAndSend("/topic/room/" + roomId, content);
    }

    // ✅ Start the quiz game (Only the host can start it)
    public void startGame(String roomId, Long hostId) {
        QuizRoom quizRoom = quizRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!hostId.equals(quizRoom.getHostId())) {
            throw new SecurityException("Only the host can start the game");
        }

        if (quizRoom.isStarted()) {
            throw new IllegalStateException("Game already started");
        }

        quizRoom.setStarted(true);
        quizRoomRepository.save(quizRoom);

        messagingTemplate.convertAndSend("/topic/room/" + roomId,
                Map.of("type", "GAME_STARTED", "message", "The game has begun! 🎮"));

        // Start sending questions
        sendNextQuestion(roomId);
    }

    // ✅ Send the next question
    public void sendNextQuestion(String roomId) {
        QuizRoom quizRoom = quizRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (!quizRoom.isStarted()) {
            throw new IllegalStateException("Game not started");
        }

        // Retrieve questions from GameService
        List<Question> questions = gameService.graduallyIncreasingDifficulty(10);
        int currentIndex = questionIndex.getAndIncrement();

        // Check if all questions are exhausted
        if (currentIndex >= questions.size()) {
            endGame(roomId);
            return;
        }

        Question currentQuestion = questions.get(currentIndex);
        Map<String, Object> questionPayload = Map.of(
                "type", "QUESTION",
                "question", currentQuestion.getQuestion(),
                "options", currentQuestion.getOptions()
        );

        messagingTemplate.convertAndSend("/topic/room/" + roomId, questionPayload);

        // Schedule next question after 20 seconds
        new Thread(() -> {
            try {
                Thread.sleep(20000);
                sendNextQuestion(roomId);
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    // ✅ End the game when all questions are answered
    private void endGame(String roomId) {
        QuizRoom quizRoom = quizRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        quizRoom.setStarted(false);  // Mark game as ended
        quizRoomRepository.save(quizRoom);

        messagingTemplate.convertAndSend("/topic/room/" + roomId,
                Map.of("type", "GAME_ENDED", "message", "The quiz has finished! 🏆"));
    }
}

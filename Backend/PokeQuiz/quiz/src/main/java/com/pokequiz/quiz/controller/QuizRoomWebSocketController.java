package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.dto.WebSocketQuizMessageDTO;
import com.pokequiz.quiz.service.WSService;
import jakarta.validation.Valid;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
public class QuizRoomWebSocketController {

    private final WSService wsService;

    public QuizRoomWebSocketController(WSService wsService) {
        this.wsService = wsService;
    }

    // 🎤 Chat Message: Players send chat messages in a room
    @MessageMapping("/room/{roomId}/chat")
    public void sendMessage(@DestinationVariable String roomId, @Valid @Payload WebSocketQuizMessageDTO message) {
        wsService.sendMessage(roomId, message);
    }

    // 📊 Submit Answer: Players submit their answers
//    @MessageMapping("/room/{roomId}/answer")
//    public void submitAnswer(@DestinationVariable String roomId, @Valid @Payload WebSocketQuizMessageDTO message) {
//        wsService.handleAnswer(roomId, message);
//    }

    // 🎮 Start Game: Host initiates the quiz game
    @MessageMapping("/room/{roomId}/start")
    public void startGame(@DestinationVariable String roomId, @Valid @Payload WebSocketQuizMessageDTO message) {
        wsService.startGame(roomId, message.getUserId());
    }
}

package com.pokequiz.quiz.controller;

import com.pokequiz.quiz.dto.PlayerDTO;
import com.pokequiz.quiz.dto.QuizRoomDTO;
import com.pokequiz.quiz.model.Player;
import com.pokequiz.quiz.model.QuizRoom;
import com.pokequiz.quiz.service.QuizRoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz/room")
public class QuizRoomController {

    private final QuizRoomService quizRoomService;

    public QuizRoomController(QuizRoomService quizRoomService) {
        this.quizRoomService = quizRoomService;
    }

    // ✅ Create a new quiz room and join as host
    @PostMapping("/create")
    public ResponseEntity<QuizRoom> createRoom(
            @Valid @RequestBody QuizRoomDTO roomDto,
            @Valid @RequestBody PlayerDTO playerDto) {
        QuizRoom createdRoom = quizRoomService.createRoom(roomDto, playerDto);
        return ResponseEntity.ok(createdRoom);
    }

    // ✅ Join an existing quiz room
    @PostMapping("/join/{roomId}")
    public ResponseEntity<Player> joinRoom(
            @PathVariable String roomId,
            @Valid @RequestBody PlayerDTO playerDto) {
        Player player = quizRoomService.joinRoom(roomId, playerDto);
        return ResponseEntity.ok(player);
    }

    // ✅ Get a list of available quiz rooms
    @GetMapping("/list")
    public ResponseEntity<List<QuizRoom>> listRooms() {
        List<QuizRoom> rooms = quizRoomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }
}

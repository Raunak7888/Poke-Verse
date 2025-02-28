package com.pokequiz.quiz.service;

import com.pokequiz.quiz.dto.PlayerDTO;
import com.pokequiz.quiz.dto.QuizRoomDTO;
import com.pokequiz.quiz.model.Player;
import com.pokequiz.quiz.model.QuizRoom;
import com.pokequiz.quiz.model.QuizRound;
import com.pokequiz.quiz.repository.PlayerRepository;
import com.pokequiz.quiz.repository.QuizRoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizRoomService {

    private final QuizRoomRepository quizRoomRepository;
    private final PlayerRepository playerRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public QuizRoomService(QuizRoomRepository quizRoomRepository,
                           PlayerRepository playerRepository,
                           SimpMessagingTemplate messagingTemplate) {
        this.quizRoomRepository = quizRoomRepository;
        this.playerRepository = playerRepository;
        this.messagingTemplate = messagingTemplate;
    }

    // ✅ Create a new quiz room and add the host as a player
    public QuizRoom createRoom(QuizRoomDTO roomDto, PlayerDTO playerDto) {
        if (!Objects.equals(roomDto.getHostId(), playerDto.getUserId())) {
            throw new IllegalArgumentException("HostId does not match userId");
        }

        QuizRoom room = new QuizRoom(roomDto.getName(), roomDto.isPublic(), roomDto.getMaxPlayers());
        room.setHostId(playerDto.getUserId());

        // Create host player and associate with room
        Player player = new Player(playerDto.getUserId(), playerDto.getName(), room);
        room.addPlayer(player);

        quizRoomRepository.save(room);
        playerRepository.save(player);

        // Notify clients about the new room
        messagingTemplate.convertAndSend("/topic/room/" + room.getId(), player.getUsername() + " created the room.");

        return room;
    }

    // ✅ Join an existing quiz room
    public Player joinRoom(String roomId, PlayerDTO playerDto) {
        QuizRoom room = quizRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.isStarted()) {
            throw new IllegalStateException("Game already started");
        }

        if (room.getPlayers().size() >= room.getMaxPlayers()) {
            throw new IllegalStateException("Room is full");
        }

        Player player = new Player(playerDto.getUserId(), playerDto.getUsername(), room);
        room.addPlayer(player);

        playerRepository.save(player);
        quizRoomRepository.save(room);

        // Notify other players
        messagingTemplate.convertAndSend("/topic/room/" + roomId, player.getUsername() + " joined the room.");

        return player;
    }

    // ✅ Get a list of all quiz rooms
    public List<QuizRoom> getAllRooms() {
        return quizRoomRepository.findAll();
    }
}

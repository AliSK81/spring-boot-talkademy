package com.alisk.chatroom.controller;


import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.request.RoomAddRequest;
import com.alisk.chatroom.service.RoomService;
import com.alisk.chatroom.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@AllArgsConstructor
@RestController
public class RoomController {
    private final RoomService roomService;


    @PostMapping("/rooms")
    public ResponseEntity<Object> addRoom(@RequestBody RoomAddRequest request) {
        Room entity = Mapper.map(request, Room.class);
        Room savedRoom = roomService.addRoom(entity, request.getOwnerId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedRoom.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> delRoom(@PathVariable Integer roomId) {
        roomService.delRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<Set<Room>> getRooms() {
        return ResponseEntity.ok(roomService.getRooms());
    }

    @PatchMapping("rooms/{roomId}/join/{userId}")
    public ResponseEntity<Object> join(@PathVariable Integer roomId,
                                       @PathVariable Integer userId) {
        roomService.join(roomId, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/rooms/{roomId}/leave/{userId}")
    public ResponseEntity<Object> leave(@PathVariable Integer roomId,
                                        @PathVariable Integer userId) {
        roomService.leave(roomId, userId);
        return ResponseEntity.ok().build();
    }

}

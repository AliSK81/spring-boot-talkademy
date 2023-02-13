package com.alisk.chatroom.service;

import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl roomService;

    @Mock
    private RoomRepository roomRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private MessageRepository messageRepo;

    @Test
    void addRoomTest() {
        Room entityModel = Room.builder().build();
        User owner = User.builder().id(1).build();
        when(roomRepo.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));
        when(userRepo.findById(owner.getId())).thenReturn(Optional.of(owner));

        Room actual = roomService.addRoom(entityModel, owner.getId());

        verify(roomRepo).save(any(Room.class));
        assertEquals(owner, actual.getOwner());
        assertTrue(actual.getMembers().contains(owner));
    }

    @Test
    void delRoomTest() {
        Room room = Room.builder().id(1).build();
        roomService.delRoom(room.getId());

        verify(messageRepo).deleteByRoomId(room.getId());
        verify(roomRepo).deleteById(room.getId());
    }

    @Test
    void getRoomsTest() {
        when(roomRepo.findAll()).thenReturn(new HashSet<>(List.of(
                Room.builder().id(1).build(),
                Room.builder().id(2).build()
        )));

        Set<Room> rooms = roomService.getRooms();

        assertEquals(2, rooms.size());
    }

    @Test
    void joinTest() {
        User user = User.builder().id(1).build();
        User owner = User.builder().id(2).build();
        Room room = Room.builder().id(1).owner(owner)
                .members(new HashSet<>(List.of(owner))).build();

        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(roomRepo.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room updatedRoom = roomService.join(room.getId(), user.getId());

        verify(roomRepo).save(updatedRoom);
        assertTrue(updatedRoom.getMembers().contains(user));
        assertEquals(2, updatedRoom.getMembers().size());
    }

    @Test
    void leaveMemberTest() {
        User user = User.builder().id(1).build();
        User owner = User.builder().id(2).build();
        Room room = Room.builder().id(1).owner(owner)
                .members(new HashSet<>(List.of(owner, user))).build();

        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(roomRepo.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room updatedRoom = roomService.leave(room.getId(), user.getId());

        verify(roomRepo).save(updatedRoom);
        assertFalse(updatedRoom.getMembers().contains(user));
        assertTrue(updatedRoom.getMembers().contains(owner));
    }

    @Test
    void leaveOwnerTest() {
        User user = User.builder().id(1).build();
        User owner = User.builder().id(2).build();
        Room room = Room.builder().id(1).owner(owner)
                .members(new HashSet<>(List.of(owner, user))).build();

        when(userRepo.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(roomRepo.save(any(Room.class))).thenAnswer(i -> i.getArgument(0));

        Room updatedRoom = roomService.leave(room.getId(), owner.getId());

        verify(roomRepo).deleteById(room.getId());
        assertNull(updatedRoom);
    }
}
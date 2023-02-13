package com.alisk.chatroom.service;

import com.alisk.chatroom.exception.NoSuchMemberException;
import com.alisk.chatroom.exception.RoomNotFoundException;
import com.alisk.chatroom.exception.UserNotFoundException;
import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepo;
    private final UserRepository userRepo;

    private final MessageRepository messageRepo;

    @Override
    public Room addRoom(Room room, Integer ownerId) {
        User owner = getUser(ownerId);
        room.setOwner(owner);
        room.setMembers(new HashSet<>(List.of(owner)));
        return roomRepo.save(room);
    }

    @Override
    public void delRoom(Integer roomId) {
        try {
            messageRepo.deleteByRoomId(roomId);
            roomRepo.deleteById(roomId);
        } catch (EmptyResultDataAccessException e) {
            throw new RoomNotFoundException();
        }
    }

    @Override
    public Set<Room> getRooms() {
        return roomRepo.findAll();
    }

    @Override
    public Room join(Integer roomId, Integer userId) {
        Room room = getRoom(roomId);
        User user = getUser(userId);
        room.getMembers().add(user);
        return roomRepo.save(room);
    }

    @Override
    public Room leave(Integer roomId, Integer userId) {
        Room room = getRoom(roomId);
        User user = getUser(userId);

        // maybe this exception is not necessary
        if (!room.getMembers().contains(user)) {
            throw new NoSuchMemberException();
        }

        if (room.getOwner().equals(user)) {
            roomRepo.deleteById(roomId);
            return null;
        } else {
            room.getMembers().remove(user);
            return roomRepo.save(room);
        }
    }

    private User getUser(Integer userId) {
        return userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private Room getRoom(Integer roomId) {
        return roomRepo.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }
}

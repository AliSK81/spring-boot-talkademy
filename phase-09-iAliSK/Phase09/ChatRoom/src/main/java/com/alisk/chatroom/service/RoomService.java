package com.alisk.chatroom.service;

import com.alisk.chatroom.model.Room;

import java.util.Set;

public interface RoomService {

    Room addRoom(Room room, Integer ownerId);

    void delRoom(Integer roomId);

    Set<Room> getRooms();

    Room join(Integer roomId, Integer userId);

    Room leave(Integer roomId, Integer userId);

}

package com.alisk.chatroom.repository;

import com.alisk.chatroom.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface RoomRepository extends CrudRepository<Room, Integer> {
    Set<Room> findAll();
}

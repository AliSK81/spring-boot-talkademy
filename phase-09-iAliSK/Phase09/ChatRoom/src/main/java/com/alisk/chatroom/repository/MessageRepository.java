package com.alisk.chatroom.repository;

import com.alisk.chatroom.model.Message;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface MessageRepository extends CrudRepository<Message, Integer> {
    Integer countByRoomId(Integer roomId);

    Integer countBySenderId(Integer userId);

    @Transactional
    void deleteByRoomId(Integer roomId);

}

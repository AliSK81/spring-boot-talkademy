package com.alisk.chatroom.service;

import com.alisk.chatroom.exception.MessageNotFoundException;
import com.alisk.chatroom.exception.NoSuchMemberException;
import com.alisk.chatroom.exception.RoomNotFoundException;
import com.alisk.chatroom.exception.UserNotFoundException;
import com.alisk.chatroom.model.Message;
import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import com.alisk.chatroom.util.Time;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepo;
    private final RoomRepository roomRepo;
    private final UserRepository userRepo;

    @Override
    public Message sendMessage(String text, Integer roomId, Integer senderId) {

        Room room = getRoom(roomId);
        User sender = getUser(senderId);

        if (!room.getMembers().contains(sender)) {
            throw new NoSuchMemberException();
        }

        Message message = Message.builder()
                .text(text)
                .room(room)
                .sender(sender)
                .createdAt(Time.now())
                .build();

        return messageRepo.save(message);
    }

    @Override
    public Message editMessage(Integer messageId, String text) {
        Message message = getMessage(messageId);
        message.setText(text);
        message.setUpdatedAt(Time.now());
        return messageRepo.save(message);
    }

    @Override
    public void delMessage(Integer messageId) {
        try {
            messageRepo.deleteById(messageId);
        } catch (EmptyResultDataAccessException e) {
            throw new MessageNotFoundException();
        }
    }


    private User getUser(Integer userId) {
        return userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    private Room getRoom(Integer roomId) {
        return roomRepo.findById(roomId).orElseThrow(RoomNotFoundException::new);
    }

    private Message getMessage(Integer messageId) {
        return messageRepo.findById(messageId).orElseThrow(MessageNotFoundException::new);
    }

}

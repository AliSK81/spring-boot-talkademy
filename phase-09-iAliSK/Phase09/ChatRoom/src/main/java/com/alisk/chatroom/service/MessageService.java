package com.alisk.chatroom.service;

import com.alisk.chatroom.model.Message;

public interface MessageService {

    Message sendMessage(String text, Integer roomId, Integer senderId);

    Message editMessage(Integer messageId, String text);

    void delMessage(Integer messageId);

}

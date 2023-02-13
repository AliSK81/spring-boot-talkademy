package com.alisk.chatroom.exception;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
        super("message not found!");
    }
}

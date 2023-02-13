package com.alisk.chatroom.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException() {
        super("room not exist!");
    }
}

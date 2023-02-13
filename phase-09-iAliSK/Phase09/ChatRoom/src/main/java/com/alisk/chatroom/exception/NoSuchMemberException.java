package com.alisk.chatroom.exception;

public class NoSuchMemberException extends RuntimeException {

    public NoSuchMemberException() {
        super("you're not a member of the room!");
    }
}

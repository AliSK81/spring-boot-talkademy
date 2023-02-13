package com.alisk.chatroom.exception;

public class SessionTimeOutException extends RuntimeException {

    public SessionTimeOutException() {
        super("Your session has been expired!");
    }
}

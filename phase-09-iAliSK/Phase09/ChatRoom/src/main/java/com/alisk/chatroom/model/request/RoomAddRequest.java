package com.alisk.chatroom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RoomAddRequest {
    private String title;
    private Integer ownerId;
}
package com.alisk.chatroom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageSendRequest {
    private String text;
    private Integer senderId;
    private Integer roomId;
}

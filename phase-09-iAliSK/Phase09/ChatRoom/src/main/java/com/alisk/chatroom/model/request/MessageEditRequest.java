package com.alisk.chatroom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MessageEditRequest {
    private String text;
}

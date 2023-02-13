package com.alisk.chatroom.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserAddRequest {
    private String name;
    private String avatar;
    private String bio;
}

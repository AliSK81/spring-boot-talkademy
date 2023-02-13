package com.alisk.chatroom.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserViewProfileResponse {
    private String name;
    private String avatar;
    private String bio;
}

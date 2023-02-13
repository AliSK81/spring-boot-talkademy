package com.alisk.chatroom.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class UserReport {
    private Integer userId;
    private Integer messages;
}

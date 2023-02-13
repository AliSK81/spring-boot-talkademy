package com.alisk.chatroom.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class RoomReport {
    private Integer roomId;
    private Integer members;
    private Integer messages;
}

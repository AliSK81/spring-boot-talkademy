package com.alisk.chatroom.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class GeneralReport {
    private Integer totalUsers;
    private Integer totalRooms;
    private Integer totalMessages;
}

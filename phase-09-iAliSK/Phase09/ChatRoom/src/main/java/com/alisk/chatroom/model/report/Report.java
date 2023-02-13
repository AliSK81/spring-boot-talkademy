package com.alisk.chatroom.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@AllArgsConstructor
@Data
public class Report {
    private Long reportId;
    private LocalDateTime createdAt;
    private GeneralReport generalReport;
    private Set<UserReport> usersReport;
    private Set<RoomReport> roomsReport;
}

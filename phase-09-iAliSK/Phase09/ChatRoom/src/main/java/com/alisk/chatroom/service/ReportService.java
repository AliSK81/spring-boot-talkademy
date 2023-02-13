package com.alisk.chatroom.service;

import com.alisk.chatroom.model.report.GeneralReport;
import com.alisk.chatroom.model.report.RoomReport;
import com.alisk.chatroom.model.report.UserReport;

import java.util.Set;

public interface ReportService {
    Set<RoomReport> getRoomsReport();

    Set<UserReport> getUsersReport();

    GeneralReport getGeneralReport();

}

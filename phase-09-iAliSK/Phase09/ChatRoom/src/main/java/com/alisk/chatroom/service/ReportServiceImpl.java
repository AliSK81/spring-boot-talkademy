package com.alisk.chatroom.service;

import com.alisk.chatroom.model.report.GeneralReport;
import com.alisk.chatroom.model.report.RoomReport;
import com.alisk.chatroom.model.report.UserReport;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final UserRepository userRepo;
    private final RoomRepository roomRepo;
    private final MessageRepository messageRepo;

    @Override
    public Set<RoomReport> getRoomsReport() {

        Set<RoomReport> roomReports = new HashSet<>();

        roomRepo.findAll().forEach(room -> {
            roomReports.add(RoomReport.builder()
                    .roomId(room.getId())
                    .members(room.getMembers().size())
                    .messages(messageRepo.countByRoomId(room.getId()))
                    .build());
        });

        return roomReports;
    }

    @Override
    public Set<UserReport> getUsersReport() {

        Set<UserReport> userReports = new HashSet<>();

        userRepo.findAll().forEach(user -> {
            userReports.add(UserReport.builder()
                    .userId(user.getId())
                    .messages(messageRepo.countBySenderId(user.getId()))
                    .build());
        });

        return userReports;
    }

    @Override
    public GeneralReport getGeneralReport() {
        return GeneralReport.builder()
                .totalUsers((int) userRepo.count())
                .totalRooms((int) roomRepo.count())
                .totalMessages((int) messageRepo.count())
                .build();
    }

}

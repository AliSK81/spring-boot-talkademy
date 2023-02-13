package com.alisk.chatroom.service;

import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.model.report.GeneralReport;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


@SpringBootTest
class ReportServiceImplTest {

    @InjectMocks
    private ReportServiceImpl reportService;

    @Mock
    private MessageRepository messageRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private RoomRepository roomRepo;


    @Test
    void getRoomsReportTest() {
        Room room1 = spy(Room.builder().id(1).build());
        Room room2 = spy(Room.builder().id(2).build());

        Set<User> members1 = spy(new HashSet<>());
        Set<User> members2 = spy(new HashSet<>());

        when(members1.size()).thenReturn(10);
        when(members2.size()).thenReturn(20);

        when(room1.getMembers()).thenReturn(members1);
        when(room2.getMembers()).thenReturn(members2);

        when(messageRepo.countByRoomId(room1.getId())).thenReturn(100);
        when(messageRepo.countByRoomId(room2.getId())).thenReturn(200);

        when(roomRepo.findAll()).thenReturn(new HashSet<>(List.of(room1, room2)));

        reportService.getRoomsReport().forEach(report -> {
            if (report.getRoomId().equals(room1.getId())) {
                assertEquals(10, report.getMembers());
                assertEquals(100, report.getMessages());
            } else {
                assertEquals(20, report.getMembers());
                assertEquals(200, report.getMessages());
            }
        });
    }

    @Test
    void getUsersReport() {
        User user1 = User.builder().id(1).build();
        User user2 = User.builder().id(2).build();

        when(messageRepo.countBySenderId(user1.getId())).thenReturn(10);
        when(messageRepo.countBySenderId(user2.getId())).thenReturn(20);
        when(userRepo.findAll()).thenReturn(new HashSet<>(List.of(user1, user2)));

        reportService.getUsersReport().forEach(report -> {
            if (report.getUserId().equals(user1.getId())) {
                assertEquals(10, report.getMessages());
            } else {
                assertEquals(20, report.getMessages());
            }
        });
    }

    @Test
    void getGeneralReport() {

        when(roomRepo.count()).thenReturn(10L);
        when(userRepo.count()).thenReturn(20L);
        when(messageRepo.count()).thenReturn(30L);

        GeneralReport report = reportService.getGeneralReport();

        assertEquals(10, report.getTotalRooms());
        assertEquals(20, report.getTotalUsers());
        assertEquals(30, report.getTotalMessages());

    }
}
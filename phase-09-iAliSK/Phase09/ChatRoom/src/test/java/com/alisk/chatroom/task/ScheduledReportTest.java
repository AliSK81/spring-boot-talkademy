package com.alisk.chatroom.task;

import com.alisk.chatroom.model.report.GeneralReport;
import com.alisk.chatroom.model.report.RoomReport;
import com.alisk.chatroom.model.report.UserReport;
import com.alisk.chatroom.service.ReportService;
import com.alisk.chatroom.util.Time;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledReportTest {

    @InjectMocks
    private ScheduledReport scheduledReport;

    @Mock
    private ReportService reportService;

    @Value("${report.directory}")
    private String testReportDir;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(scheduledReport, "reportDir", testReportDir);
        ReflectionTestUtils.setField(Time.class, "zoneId", "UTC");
    }


    @SneakyThrows
    @Test
    void generateReportTest() {

        when(reportService.getGeneralReport()).thenReturn(buildGeneralReport());
        when(reportService.getRoomsReport()).thenReturn(buildRoomsReport());
        when(reportService.getUsersReport()).thenReturn(buildUsersReport());


        String instantExpected = "2022-09-12T19:05:45.919301Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneOffset.UTC);
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);

            scheduledReport.generateReport();

            File expectedFile = new File("src/test/resources/reports/exceptedReport.json");
            File actualFile = getGeneratedReport();

            if (!actualFile.exists()) {
                fail("Report log not generated!");
            }

            assertTrue(FileUtils.contentEquals(expectedFile, actualFile));
        }
    }

    private File getGeneratedReport() {
        long reportId = Instant.now().getEpochSecond();
        return new File(String.format("%s/report-%d.json", testReportDir, reportId));
    }

    private GeneralReport buildGeneralReport() {
        return GeneralReport.builder()
                .totalRooms(2)
                .totalUsers(3)
                .totalMessages(15)
                .build();
    }

    private Set<RoomReport> buildRoomsReport() {
        return new HashSet<>(List.of(
                RoomReport.builder().roomId(1).members(3).messages(5).build(),
                RoomReport.builder().roomId(2).members(2).messages(10).build()
        ));
    }

    private Set<UserReport> buildUsersReport() {
        return new HashSet<>(List.of(
                UserReport.builder().userId(1).messages(5).build(),
                UserReport.builder().userId(2).messages(3).build(),
                UserReport.builder().userId(3).messages(7).build()
        ));
    }
}
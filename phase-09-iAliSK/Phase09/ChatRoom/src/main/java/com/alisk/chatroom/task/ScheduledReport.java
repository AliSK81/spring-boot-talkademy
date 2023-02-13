package com.alisk.chatroom.task;

import com.alisk.chatroom.model.report.Report;
import com.alisk.chatroom.service.ReportService;
import com.alisk.chatroom.util.Mapper;
import com.alisk.chatroom.util.Time;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class ScheduledReport {
    private final ReportService reportService;

    @Value("${report.directory}")
    private String reportDir;


    @Scheduled(cron = "0 0 1 * * MON")
    public void generateReport() {
        Report report = Report.builder()
                .reportId(Time.currSeconds())
                .createdAt(Time.now())
                .generalReport(reportService.getGeneralReport())
                .roomsReport(reportService.getRoomsReport())
                .usersReport(reportService.getUsersReport())
                .build();

        File file = new File(String.format("%s/report-%d.json", reportDir, report.getReportId()));

        PrintWriter out = null;
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            out = new PrintWriter(new FileWriter(file));
            out.write(Mapper.mapToJson(report));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) out.close();
        }
    }

}


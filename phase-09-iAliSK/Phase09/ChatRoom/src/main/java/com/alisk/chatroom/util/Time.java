package com.alisk.chatroom.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class Time {

    @Value(value = "${time.zone}")
    private static String zoneId;

    public static LocalDateTime toLDT(Instant i) {
        return LocalDateTime.ofInstant(i, ZoneId.of(zoneId));
    }

    public static LocalDateTime now() {
        return toLDT(Instant.now());
    }

    public static long currSeconds() {
        return Instant.now().getEpochSecond();
    }
}

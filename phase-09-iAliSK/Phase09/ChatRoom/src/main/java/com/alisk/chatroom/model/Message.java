package com.alisk.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;

    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;
}

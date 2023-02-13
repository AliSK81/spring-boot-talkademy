package com.alisk.chatroom.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private Integer id;

    private String title;

    @ManyToMany
    @JoinTable(name = "member",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;
}

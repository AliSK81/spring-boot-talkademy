package com.alisk.chatroom.service;

import com.alisk.chatroom.model.Message;
import com.alisk.chatroom.model.Room;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.MessageRepository;
import com.alisk.chatroom.repository.RoomRepository;
import com.alisk.chatroom.repository.UserRepository;
import com.alisk.chatroom.util.Time;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepo;
    @Mock
    private RoomRepository roomRepo;
    @Mock
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(Time.class, "zoneId", "UTC");
    }

    @Test
    void sendMessageTest() {
        String text = "test";

        User user = User.builder().id(1).build();
        User owner = User.builder().id(2).build();
        Room room = Room.builder().id(1).owner(owner)
                .members(new HashSet<>(List.of(owner, user))).build();

        when(roomRepo.findById(room.getId())).thenReturn(Optional.of(room));
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(messageRepo.save(any(Message.class))).thenAnswer(i -> i.getArgument(0));


        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);

            Message sentMessage = messageService.sendMessage(text, room.getId(), user.getId());

            verify(messageRepo).save(sentMessage);

            assertEquals(text, sentMessage.getText());
            assertEquals(user, sentMessage.getSender());
            assertEquals(room, sentMessage.getRoom());

            assertEquals(Time.toLDT(instant), sentMessage.getCreatedAt());
        }

    }

    @Test
    void editMessageTest() {
        Message message = Message.builder().id(1).text("test").build();
        String newText = "edited";

        when(messageRepo.save(any(Message.class))).thenAnswer(i -> i.getArgument(0));
        when(messageRepo.findById(message.getId())).thenReturn(Optional.of(message));

        Clock clock = Clock.fixed(Instant.now(), ZoneOffset.UTC);
        Instant instant = Instant.now(clock);

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(instant);

            Message editedMessage = messageService.editMessage(message.getId(), newText);

            verify(messageRepo).save(editedMessage);

            assertEquals(newText, editedMessage.getText());

            assertEquals(Time.toLDT(instant), message.getUpdatedAt());
        }
    }

    @Test
    void delMessageTest() {
        int messageId = 1;
        doNothing().when(messageRepo).deleteById(messageId);
        messageService.delMessage(messageId);
        verify(messageRepo).deleteById(messageId);
    }
}
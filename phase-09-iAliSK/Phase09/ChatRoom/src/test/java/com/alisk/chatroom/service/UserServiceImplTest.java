package com.alisk.chatroom.service;

import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;


    @Test
    void addUserTest() {
        User entityModel = User.builder().build();
        User savedUser = User.builder().id(1).build();
        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        User actual = userService.addUser(entityModel);

        verify(userRepo).save(any(User.class));
        assertEquals(savedUser, actual);
    }

    @Test
    void getUserTest() {
        User user = User.builder().id(1).build();
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        User foundUser = userService.getUser(user.getId());
        assertEquals(user, foundUser);
    }
}
package com.alisk.chatroom.service;

import com.alisk.chatroom.exception.UserNotFoundException;
import com.alisk.chatroom.model.User;
import com.alisk.chatroom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    @Override
    public User addUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User getUser(Integer userId) {
        return userRepo.findById(userId).orElseThrow(UserNotFoundException::new);
    }

}

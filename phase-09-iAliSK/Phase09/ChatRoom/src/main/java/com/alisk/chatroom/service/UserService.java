package com.alisk.chatroom.service;

import com.alisk.chatroom.model.User;

public interface UserService {

    User addUser(User user);

    User getUser(Integer userId);


}

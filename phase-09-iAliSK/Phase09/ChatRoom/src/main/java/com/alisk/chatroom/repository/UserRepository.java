package com.alisk.chatroom.repository;

import com.alisk.chatroom.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}

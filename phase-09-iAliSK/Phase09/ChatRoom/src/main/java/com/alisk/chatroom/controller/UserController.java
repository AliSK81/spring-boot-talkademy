package com.alisk.chatroom.controller;

import com.alisk.chatroom.model.User;
import com.alisk.chatroom.model.request.UserAddRequest;
import com.alisk.chatroom.model.response.UserViewProfileResponse;
import com.alisk.chatroom.service.UserService;
import com.alisk.chatroom.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> addUser(@RequestBody UserAddRequest request) {
        User entity = Mapper.map(request, User.class);
        User savedUser = userService.addUser(entity);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserViewProfileResponse> getUserProfile(@PathVariable Integer userId) {
        User user = userService.getUser(userId);
        UserViewProfileResponse response = Mapper.map(user, UserViewProfileResponse.class);
        return ResponseEntity.ok(response);
    }


}

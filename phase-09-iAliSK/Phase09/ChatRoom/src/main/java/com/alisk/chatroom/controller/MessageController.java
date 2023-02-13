package com.alisk.chatroom.controller;


import com.alisk.chatroom.model.Message;
import com.alisk.chatroom.model.request.MessageEditRequest;
import com.alisk.chatroom.model.request.MessageSendRequest;
import com.alisk.chatroom.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<Object> sendMessage(@RequestBody MessageSendRequest request) {

        Message savedMessage = messageService
                .sendMessage(request.getText(), request.getRoomId(), request.getSenderId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedMessage.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/messages/{messageId}")
    public ResponseEntity<Object> editMessage(@PathVariable Integer messageId,
                                              @RequestBody MessageEditRequest request) {
        messageService.editMessage(messageId, request.getText());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Void> delMessage(@PathVariable Integer messageId) {
        messageService.delMessage(messageId);

        return ResponseEntity.noContent().build();
    }
}

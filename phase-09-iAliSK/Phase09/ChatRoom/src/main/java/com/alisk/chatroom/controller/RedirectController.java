package com.alisk.chatroom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {

    @RequestMapping("/")
    public RedirectView redirect() {
        return new RedirectView("http://localhost:8080/swagger-ui/");
    }
}
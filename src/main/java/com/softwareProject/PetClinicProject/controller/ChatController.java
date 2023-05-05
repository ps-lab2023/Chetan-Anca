package com.softwareProject.PetClinicProject.controller;

import com.softwareProject.PetClinicProject.chat.Greeting;
import com.softwareProject.PetClinicProject.chat.HelloMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;


@RestController
@Getter
@Setter
public class ChatController {

    private List<String> messages = new ArrayList<>();

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000);
        messages.add(HtmlUtils.htmlEscape(message.getName()));
        return new Greeting(HtmlUtils.htmlEscape(message.getName()));
    }

    @RequestMapping("/messages")
    public ResponseEntity getMessages() {
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }
}

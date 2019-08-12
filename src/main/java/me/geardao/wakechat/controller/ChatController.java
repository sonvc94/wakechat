package me.geardao.wakechat.controller;

import me.geardao.wakechat.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat.sendMessage")
    @SendTo({"/topic/public", "/topic/vnpay"})
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        log.info("Received message: {}", chatMessage);
        return chatMessage;
    }


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/notifyVNPAY")
    @SendTo("/topic/vnpay")
    public ChatMessage addVnPayer(ChatMessage chatMessage){
        log.info("VNPAYER connected: {}", chatMessage);
        return chatMessage;
    }
}

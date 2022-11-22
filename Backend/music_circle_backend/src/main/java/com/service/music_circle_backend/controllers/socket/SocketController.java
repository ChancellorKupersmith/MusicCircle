package com.service.music_circle_backend.controllers.socket;


import com.service.music_circle_backend.entities.socket.ChatMessage;
import com.service.music_circle_backend.entities.socket.Message;
import com.service.music_circle_backend.entities.socket.Response;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.repos.socket.ResponseRepository;
import com.service.music_circle_backend.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class SocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

   @Autowired
    ResponseRepository responseRepository;

    @Autowired
    UserService userService;

    public SocketController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/group/{groupID}")
    public void group(@DestinationVariable int groupID, Message message) {
        log.info("Receive group message: [" + groupID + " -> " + message.getName() + "]");
        User a = userService.findByUserid((long) message.getFromUserID());
        Response response = new Response(message.getFromUserID(), a.getUsername(), message.getName());
        responseRepository.save(response);
        simpMessagingTemplate.convertAndSend("/g/" + groupID, response);
    }

    @MessageMapping("/chat")
    public void chat(ChatMessage chatMessage) {
        log.info("Receive point-to-point chat message: [" + chatMessage.getFromUserID() + " -> " + chatMessage.getUserID() + ", " + chatMessage.getMessage() + "]");
        Response response = new Response(chatMessage.getFromUserID(),chatMessage.getMessage(),"User " + chatMessage.getFromUserID() + ": " + chatMessage.getMessage());
        responseRepository.save(response);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(chatMessage.getUserID()), "/msg", response);
    }

}


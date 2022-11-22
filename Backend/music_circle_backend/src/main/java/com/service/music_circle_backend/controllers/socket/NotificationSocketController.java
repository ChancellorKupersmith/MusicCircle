package com.service.music_circle_backend.controllers.socket;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.socket.Notification;
import com.service.music_circle_backend.entities.socket.NotificationResponse;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.socket.NotificationResponseRepository;
import com.service.music_circle_backend.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin("http://10.24.227.244:8080")
public class NotificationSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    UserService userService;

    @Autowired
    NotificationResponseRepository notificationResponseRepository;

    public NotificationSocketController(SimpMessagingTemplate simpMessagingTemplate){
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    //Send Notification to user_from's followers
    @MessageMapping("/to_followers")
    public void sendToFollowers(NotificationResponse notification){
        ArrayList<User> followers = new ArrayList<User>();
        for(Long userId : notification.getUsers_to()){
            User follower = userService.getOne(userId);
            followers.add(follower);
        }

        for(User follower : followers){
            if(follower.isOnline()){
                NotificationResponse notificationResponse = new NotificationResponse(
                    notification.getChannel_id(),
                    notification.getText(),
                    notification.getTitle(),
                    notification.getUser_from(),
                    follower.getId()
                );
                sendToUser(notificationResponse);
            }
            else{
                NotificationResponse followerNotification = new NotificationResponse(notification);
                notificationResponseRepository.save(followerNotification);
            }
        }
    }

    @MessageMapping("/to_user")
    public void sendToUser(NotificationResponse notification){
        NotificationResponse notificationResponse = new NotificationResponse(notification);
        User userTo = userService.getOne(notificationResponse.getUser_to());
            // simpMessagingTemplate.convertAndSend("/notifications/user/" + userTo.getUsername(), notificationResponse);
        if(userTo.isOnline()){
            simpMessagingTemplate.convertAndSend("/notifications/user/" + userTo.getUsername(), notificationResponse);

        }
        else{
            notificationResponseRepository.save(notificationResponse);
        }
    }

//    @MessageMapping("/userOnline/{username_from}")
//    @SendTo("/notifications/online/{username_from}")
//    public List<NotificationResponse> userConnected(@PathVariable String username_from){
//        User user = userService.getUser(username_from);
//        user.setOnline(true);
//        userService.saveUser(user);
//        List<NotificationResponse> notifications = notificationResponseRepository.findByUser_to_username(username_from);
//        NotificationResponse endOfList = new NotificationResponse();
//        endOfList.setChannel_id("end_of_list");
//        notifications.add(endOfList);
//        notificationResponseRepository.deleteAllByUser_to_username(username_from);
//        return notifications;
//    }
    @MessageMapping("/userOffline/{username_from}")
    public void userDisconnected(@PathVariable String username_from){
        User user = userService.getUser(username_from);
        user.setOnline(false);
        userService.saveUser(user);
    }
}

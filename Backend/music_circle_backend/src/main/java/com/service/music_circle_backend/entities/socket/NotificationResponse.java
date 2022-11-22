package com.service.music_circle_backend.entities.socket;

import javax.persistence.*;
import java.util.List;

@Entity
public class NotificationResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private String channel_id;
    private Long user_from;
    private Long user_to;
    @ElementCollection
    private List<Long> users_to;

    public NotificationResponse(){}

    public NotificationResponse(String channel_id, String text, String title, Long user_from_id, Long user_to_id){
        this.channel_id = channel_id;
        this.text = text;
        this.title = title;
        this.user_from = user_from_id;
        this.user_to = user_to_id;
    }
    public NotificationResponse(String channel_id, String text, String title, Long user_from_id, List<Long> users_to_ids){
        this.channel_id = channel_id;
        this.text = text;
        this.title = title;
        this.user_from = user_from_id;
        this.users_to = users_to_ids;
    }

    public NotificationResponse(NotificationResponse notificationResponse){
        this.user_to = notificationResponse.getUser_to();
        this.title = notificationResponse.getTitle();
        this.text = notificationResponse.getText();
        this.user_from = notificationResponse.getUser_from();
        this.channel_id = notificationResponse.getChannel_id();
        this.users_to = notificationResponse.getUsers_to();
    }

    public String getText() {
        return text;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public String getTitle() {
        return title;
    }

    public Long getUser_from() {
        return user_from;
    }

    public Long getUser_to() {
        return user_to;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser_from(Long user_from_id) {
        this.user_from = user_from_id;
    }

    public void setUser_to(Long user_to_id) {
        this.user_to = user_to_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getUsers_to() {
        return users_to;
    }

    public void setUsers_to(List<Long> users_to_ids) {
        this.users_to = users_to_ids;
    }
}

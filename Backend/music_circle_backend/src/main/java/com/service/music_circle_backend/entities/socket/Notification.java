package com.service.music_circle_backend.entities.socket;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Notification {
    private String title;
    private String text;
    private String channel_id;
    private Long user_from;
    private Long user_to;


    public Notification(){}

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

    public void setUser_from_username(Long user_from_id) {
        this.user_from = user_from_id;
    }

    public void setUser_to_username(Long user_to_id) {
        this.user_to = user_to_id;
    }

}

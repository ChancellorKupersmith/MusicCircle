package com.service.music_circle_backend.entities.comment;

import com.service.music_circle_backend.entities.audio_file.AudioFile;
import com.service.music_circle_backend.entities.user.User;

import javax.persistence.*;

@Entity
public class Comment {
    //Variables
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String text;

    private Long commenterID;

    private Long chatID;

    private int likes;

    //Constructor
    protected Comment() {}

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Long getCommenterID() {
        return commenterID;
    }

    public void setCommenterID(Long commenterID) {
        this.commenterID = commenterID;
    }

    public Long getChatID() {
        return chatID;
    }

    public void setChatID(Long chatID) {
        this.chatID = chatID;
    }

    public Comment(String text, User commenter) {
        this.text = text;
        this.commenterID = commenter.getId();
    }


    //Get Methods
    public Long getId() { return id; }
    public String getText() { return text; }
    public Long getCommenter() { return commenterID; }

    //Set Methods
    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setCommenter(User commenter) { this.commenterID = commenter.getId(); }

}



package com.example.musiccircle.Entity;

import com.example.musiccircle.entities;

public class Comment implements entities {
    private Long id;
    private String text;
    private Long audioFileId;
    private Long commenterId;

    protected Comment() {}
    public Comment(String text, Long audioFileId, Long commenterId) {
        this.text = text;
        this.audioFileId = audioFileId;
        this.commenterId = commenterId;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public Long getAudioFileId() { return audioFileId; }
    public Long getCommenterId() { return commenterId; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setAudioFileId(Long audioFileId) { this.audioFileId = audioFileId; }
    public void setCommenterId(Long commenterId) { this.commenterId = commenterId; }

    @Override
    public int getType() { return 4; }
}

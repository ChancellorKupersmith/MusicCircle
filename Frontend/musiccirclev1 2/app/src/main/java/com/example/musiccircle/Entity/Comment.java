package com.example.musiccircle.Entity;

public class Comment implements entities {
    private Long id;
    private String text;
    private Long audioFileId;
    private String commenter;

    public Comment() {}
    public Comment(String text, Long audioFileId, String commenter) {
        this.text = text;
        this.audioFileId = audioFileId;
        this.commenter = commenter;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public Long getAudioFileId() { return audioFileId; }
    public String getCommenter() { return commenter; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
    public void setAudioFileId(Long audioFileId) { this.audioFileId = audioFileId; }
    public void setCommenterId(String commenter) { this.commenter = commenter; }

    @Override
    public int getType() { return 4; }
}

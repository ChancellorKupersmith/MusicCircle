package com.service.microservices.entities.comment;

import javax.persistence.Entity;

@Entity
public class Comment {
	//Variables
	private Long id;
	private String text;
	private Long audioFileId;
	private Long commenterId;
	
	//Constructor
	protected Comment() {}
	public Comment(String text, Long audioFileId, Long commenterId) {
		this.text = text;
		this.audioFileId = audioFileId;
		this.commenterId = commenterId;
	}
	
	//Get Methods
	public Long getId() { return id; }
	public String getText() { return text; }
	public Long getAudioFileId() { return audioFileId; }
	public Long getCommenterId() { return commenterId; }
	
	//Set Methods
	public void setId(Long id) { this.id = id; }
	public void setText(String text) { this.text = text; }
	public void setAudioFileId(Long audioFileId) { this.audioFileId = audioFileId; }
	public void setCommenterId(Long commenterId) { this.commenterId = commenterId; }
}

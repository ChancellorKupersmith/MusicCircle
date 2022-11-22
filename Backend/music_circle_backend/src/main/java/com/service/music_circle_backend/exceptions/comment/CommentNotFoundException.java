package com.service.music_circle_backend.exceptions.comment;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id){ super("Could not find comment " + id); }
}

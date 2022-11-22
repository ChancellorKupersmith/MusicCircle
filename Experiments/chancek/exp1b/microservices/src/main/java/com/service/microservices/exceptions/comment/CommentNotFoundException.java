package com.service.microservices.exceptions.comment;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(Long id) { super("Could not find comment " + id); }
}

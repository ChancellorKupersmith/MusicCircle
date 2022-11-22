package com.service.music_circle_backend.exceptions.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id){
        super("Could not find user " + id);
    }
}

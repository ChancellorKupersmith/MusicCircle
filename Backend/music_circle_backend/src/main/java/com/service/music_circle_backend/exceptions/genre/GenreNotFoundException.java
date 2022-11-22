package com.service.music_circle_backend.exceptions.genre;

public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(Long id){
        super("Could not find genre " + id);
    }
    public GenreNotFoundException(String name){
        super("Could not find genre " + name);
    }

}

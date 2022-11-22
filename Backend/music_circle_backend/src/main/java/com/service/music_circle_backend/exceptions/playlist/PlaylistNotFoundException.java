package com.service.music_circle_backend.exceptions.playlist;

public class PlaylistNotFoundException extends RuntimeException{

    public PlaylistNotFoundException(Long id){
        super("Could not find playlist " + id);
    }
}

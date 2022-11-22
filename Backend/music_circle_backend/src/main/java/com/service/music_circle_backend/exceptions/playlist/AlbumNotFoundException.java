package com.service.music_circle_backend.exceptions.playlist;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Long id){
        super("Could not find album " + id);
    }
}

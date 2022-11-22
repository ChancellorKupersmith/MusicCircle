package com.service.microservices.exceptions.playlist;

public class AlbumNotFoundException extends RuntimeException{
    public AlbumNotFoundException(Long id){
        super("Could not find album " + id);
    }
}

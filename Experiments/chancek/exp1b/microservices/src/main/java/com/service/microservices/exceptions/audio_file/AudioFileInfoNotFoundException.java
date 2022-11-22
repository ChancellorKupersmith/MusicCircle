package com.service.microservices.exceptions.audio_file;


public class AudioFileInfoNotFoundException extends RuntimeException{
    public AudioFileInfoNotFoundException(Long id){
        super("Could not find audio file info" + id);
    }
}

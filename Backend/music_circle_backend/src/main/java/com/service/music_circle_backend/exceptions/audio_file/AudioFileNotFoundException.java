package com.service.music_circle_backend.exceptions.audio_file;

public class AudioFileNotFoundException extends RuntimeException{

    public AudioFileNotFoundException(Long id){
        super("Could not find audio file " + id);
    }
}

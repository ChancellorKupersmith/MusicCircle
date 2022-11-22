package com.service.music_circle_backend.exceptions.audio_file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AudioFileNotFoundAdvice {

    public AudioFileNotFoundAdvice(){

    }

    @ResponseBody
    @ExceptionHandler(AudioFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String audioFileNotFoundHandler(AudioFileNotFoundException ex){
        return ex.getMessage();
    }
}
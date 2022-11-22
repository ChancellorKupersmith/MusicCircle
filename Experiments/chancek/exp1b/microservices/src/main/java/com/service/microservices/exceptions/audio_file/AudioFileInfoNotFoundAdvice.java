package com.service.microservices.exceptions.audio_file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AudioFileInfoNotFoundAdvice {
    public AudioFileInfoNotFoundAdvice(){

    }

    @ResponseBody
    @ExceptionHandler(AudioFileInfoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String audioFileNotFoundHandler(AudioFileInfoNotFoundException ex){
        return ex.getMessage();
    }
}

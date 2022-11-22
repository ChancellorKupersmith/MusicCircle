package com.service.microservices.exceptions.audio_file;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AudioFileNotFoundAdvice {

    public AudioFileNotFoundAdvice(){

    }

    @ResponseBody
    @ExceptionHandler(AudioFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String audioFileNoteFoundHandler(AudioFileNotFoundException ex){
        return ex.getMessage();
    }
}

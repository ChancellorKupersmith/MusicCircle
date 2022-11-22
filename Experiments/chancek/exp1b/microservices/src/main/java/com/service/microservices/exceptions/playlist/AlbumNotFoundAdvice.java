package com.service.microservices.exceptions.playlist;


import com.service.microservices.exceptions.audio_file.AudioFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AlbumNotFoundAdvice {

    public AlbumNotFoundAdvice(){

    }

    @ResponseBody
    @ExceptionHandler(AlbumNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String albumNotFoundHandler(AlbumNotFoundException ex){
        return ex.getMessage();
    }
}

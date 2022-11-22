package com.service.music_circle_backend.exceptions.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlaylistNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PlaylistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String playlistNotFoundHandler(PlaylistNotFoundException ex){
        return ex.getMessage();
    }

}

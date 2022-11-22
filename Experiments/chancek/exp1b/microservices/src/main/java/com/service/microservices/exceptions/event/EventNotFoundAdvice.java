package com.service.microservices.exceptions.event;

import com.service.microservices.exceptions.event.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EventNotFoundAdvice {

    public EventNotFoundAdvice() {}

    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String eventNotFoundHandler(EventNotFoundException ex) { return ex.getMessage(); }
}
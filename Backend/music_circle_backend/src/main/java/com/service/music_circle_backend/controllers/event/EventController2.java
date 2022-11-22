//package com.service.music_circle_backend.controllers.event;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RestController;
//import com.service.music_circle_backend.entities.event.Event;
//import com.service.music_circle_backend.repos.event.EventRepository;
//import com.service.music_circle_backend.services.event.EventService;
//import com.service.music_circle_backend.services.imageupload.UploadFileImpl;
//import com.service.music_circle_backend.services.user.UserService;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@RestController
//@CrossOrigin("http://10.24.227.244:8080")
//public class EventController2 {
//    @Autowired
//    private EventService eventService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UploadFileImpl uploadFile;
//
//    public EventController2(EventRepository eventRepository, EventService eventService, UserService userService, UploadFileImpl uploadFile) {
//        this.eventService = eventService;
//        this.userService = userService;
//        this.uploadFile = uploadFile;
//    }
//
//    @PostMapping("/event/registration")
//    @ApiOperation(value = "Registers Events by firstname,lastname,username,email,password,profile picture", notes = "Provide firstname,lastname,username,email,password and profile picture to add new user to userdb")
//    public ResponseEntity<String> EventRegistration(@ApiParam(value = "user firstname", required = true) @RequestParam("name") String name,
//                                                    @ApiParam(value = "user lastname", required = true) @RequestParam("streetAddress") String streetAddress,
//                                                    @ApiParam(value = "user username", required = true) @RequestParam("state") String state,
//                                                    @ApiParam(value = "user email", required = true) @RequestParam("city") String city,
//                                                    @ApiParam(value = "user password", required = true) @RequestParam("country") String country,
//                                                    @ApiParam(value = "user profile picture") @RequestParam("description") String description,
//                                                    @ApiParam(value = "user profile picture") @RequestParam("zipcode") String zipcode,
//                                                    @ApiParam(value = "user profile picture") @RequestParam("creator") String event_creator) {
//        Event a = new Event(name, streetAddress, state, city, country, zipcode, description,event_creator);
//        return eventService.saveEvent(a);
//    }
//
//    @GetMapping("/event/all")
//    @ApiOperation(value = "Find all Events", notes = "look up all users from userdb")
//    public List<Event> getAllEvents() {
//        return eventService.getAll();
//    }
//
//    @GetMapping("/event/findbyname/{name}")
//    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
//    public Event getEventByName(@ApiParam(value = "event name ", required = true) @PathVariable("name") String name) {
//        return eventService.getEventByName(name);
//    }
//
//    @GetMapping("/event/findbylocation/{name}")
//    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
//    public Event getEventByLocation(@ApiParam(value = "user username ", required = true) @PathVariable("name") String name) {
//        return eventService.getEventByLocation(name);
//    }
//
//    @DeleteMapping("/event/delete/{name}")
//    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
//    public void removeEvent(@ApiParam(value = "event name ", required = true) @PathVariable("name") String name) {
//        eventService.removeEvent(name);
//    }
//
//
//}



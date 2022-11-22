package com.service.music_circle_backend.controllers.event;

import com.service.music_circle_backend.entities.event.Event;
import com.service.music_circle_backend.entities.playlist.Album;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.exceptions.event.EventNotFoundException;
import com.service.music_circle_backend.messages.ResponseMessage;
import com.service.music_circle_backend.repos.event.EventRepository;
import com.service.music_circle_backend.services.event.EventService;
import com.service.music_circle_backend.services.file.BASE64DecodedMultipartFile;
import com.service.music_circle_backend.services.file.PicFileStorageService;
import com.service.music_circle_backend.services.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("http://10.24.227.244:8080")
public class EventController {
    private final EventRepository eventRepository;
    @Autowired
    private EventService eventService;
    private final UserService userService;
    @Autowired
    private PicFileStorageService picFileStorageService;

    public EventController(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }


    @GetMapping("/events")
    List<Event> all() { return eventRepository.findAll(); }
    @GetMapping("/events/{id}")
    Event one(@PathVariable Long id) { return eventRepository.findById(id).orElseThrow(()->new EventNotFoundException(id)); }
    @GetMapping("/events/name")
    List<Event> allByName(@RequestParam String name) { return eventRepository.findEventByName(name); }

    //For creating new events
    @PostMapping("/events")
    Event newEvent(@RequestBody Event newEvent) { return eventRepository.save(newEvent); }
    @PostMapping("/events/user/name/location/dateTime")
    ResponseEntity newEvent(@RequestParam String username, @RequestParam String name, @RequestParam String streetAddress, @RequestParam String state, @RequestParam String city, @RequestParam String country, @RequestParam String zipcode, @RequestParam String description, @RequestParam String usersPerforming) {
        String message = "";
        try{
            ArrayList<User> performingArtists = new ArrayList<User>();
            User user = userService.getUser(username);
            for(int i=0; i<usersPerforming.length(); i++){
                if(usersPerforming.charAt(i) != ','){
                    User artist = userService.getOne((long) Character.getNumericValue(usersPerforming.charAt(i)));
                    performingArtists.add(artist);
                }
            }
            Event newEvent = new Event(name, streetAddress, state, city, country, zipcode, description, performingArtists, user);
            eventRepository.save(newEvent);
            message = "SUCCESSFUL EVENT REGISTRATION";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            message = "Could not post event";
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

//Added
    //for creating new event
    @PostMapping("/event/registration")
    @ApiOperation(value = "Registers Events by firstname,lastname,username,email,password,profile picture", notes = "Provide firstname,lastname,username,email,password and profile picture to add new user to userdb")
    public ResponseEntity<String> EventRegistration(@ApiParam(value = "user firstname", required = true) @RequestParam("name") String name,
                                                    @ApiParam(value = "user lastname", required = true) @RequestParam("streetAddress") String streetAddress,
                                                    @ApiParam(value = "user username", required = true) @RequestParam("state") String state,
                                                    @ApiParam(value = "user email", required = true) @RequestParam("city") String city,
                                                    @ApiParam(value = "user password", required = true) @RequestParam("country") String country,
                                                    @ApiParam(value = "user profile picture") @RequestParam("description") String description,
                                                    @ApiParam(value = "user profile picture") @RequestParam("zipcode") String zipcode,
                                                    @ApiParam(value = "user profile picture") @RequestParam("creator") String event_creator_username,
                                                    @ApiParam(value = "user profile picture") @RequestParam("performers") String performers,
                                                    @ApiParam(value = "user profile picture") @RequestParam("eventPic") String eventPic) {

        List<String> list = new ArrayList<String>(Arrays.asList(performers.split(",")));
        System.out.println(list);
        List<User> list1 = new ArrayList<User>();
        for (String s : list) {
            list1.add(userService.getUser(s.trim()));
        }
        User event_creator = userService.getUser(event_creator_username);
        Event a = new Event(name, streetAddress, state, city, country, zipcode, description, event_creator, list1);
        byte[] arr = Base64.decodeBase64(eventPic);
        MultipartFile f = new BASE64DecodedMultipartFile(arr,a.getName() + "_" + String.valueOf(a.getId()) + "_" + a.getEvent_creator());
        picFileStorageService.store(f);
        a.setEventPicFilename(StringUtils.cleanPath(f.getOriginalFilename()));
        return eventService.saveEvent(a);
    }

//Added
    @GetMapping("/event/findbylocation/{name}")
    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
    public List<Event> getEventByLocation(@ApiParam(value = "user username ", required = true) @PathVariable("name") String name) {
        return eventService.getEventsByState(name);
    }


    //For updating events
    //Updates location
    @PutMapping("/events/{id}/location")
    Event replaceEventLocation(@PathVariable Long id, @RequestParam("location") String location) {
        return eventRepository.findById(id).map(event -> {
            event.setLocation(location);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }
    //Updates dateTime
    @PutMapping("events/{id}/dateTime")
    Event replaceEventDateTime(@PathVariable Long id, @RequestParam("dateTime") String dateTime) {
        return eventRepository.findById(id).map(event -> {
            event.setDateTime(dateTime);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }
    //Updates description
    @PutMapping("/events/{id}/description")
    Event replaceEventDescription(@PathVariable Long id, @RequestParam("text") String description) {
        return eventRepository.findById(id).map(event -> {
            event.setDescription(description);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }
    //Updates usersPerforming
    @PutMapping("/events/{id}/usersPerforming")
    Event replaceEventUsersPerforming(@PathVariable Long id, @RequestParam("usersPerforming") List<User> usersPerforming) {
        return eventRepository.findById(id).map(event -> {
            event.setUsersPerforming(usersPerforming);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }
    //Updates usersAttending
    @PutMapping("/events/{id}/usersAttending")
    Event replaceEventUsersAttending(@PathVariable Long id, @RequestParam("usersAttending") String usersAttending) {
        return eventRepository.findById(id).map(event -> {
            List<String> list = new ArrayList<String>(Arrays.asList(usersAttending.split(",")));
            System.out.println(list);
            List<User> usersAttendingList = new ArrayList<User>();
            for (String s : list) {
                usersAttendingList.add(userService.getUser(s.trim()));
            }
            event.setUsersAttending(usersAttendingList);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }

    @GetMapping("/events/by_user")
    List<Event> getEventsByUser(@RequestParam("username") String username){
        List<Event> allEvents = eventRepository.findAll();
        List<Event> userEvents = new ArrayList<Event>();
        try{
            User user = userService.getUser(username);
            boolean inEvent;
            for(Event event : allEvents){
                inEvent = false;
                List<User> attending = event.getUsersAttending();
                List<User> performing = event.getUsersPerforming();
                for(User u : performing){
                    if(u.getId() == user.getId()){
                        userEvents.add(event);
                        inEvent = true;
                        break;
                    }
                }
                if(!inEvent){
                    for(User u : attending){
                        if(u.getId() == user.getId()){
                            userEvents.add(event);
                            break;
                        }
                    }
                }
            }
            return userEvents;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/events/by_name")
    public List<Event> getEventsByName(@RequestParam("name") String name){
        return eventRepository.findEventsByName(name);
    }
    @PutMapping("/events/{id}/change_pic")
    public ResponseEntity changeEventPic(@PathVariable Long id, @RequestParam("eventPic")MultipartFile eventPic){
        String message = "";
        try{
            Event event = eventRepository.getOne(id);
            event.setEventPicFilename(StringUtils.cleanPath(eventPic.getOriginalFilename()));
            picFileStorageService.store(eventPic);
            eventRepository.save(event);
            message = "Successfully changed event picture";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }catch (Exception e){
            e.printStackTrace();
            message = "Couldn't change event pic";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

//Event pic


    @GetMapping("/events/{id}/get_pic")
    public String getEventPic(@PathVariable Long id){
        try{
            Event event = eventRepository.getOne(id);
            byte[] a = Files.readAllBytes(picFileStorageService.getPath(event.getEventPicFilename()));
            String base64 = Base64.encodeBase64String(a);
            return base64;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @DeleteMapping("/events/{id}")
    void deleteEvent(@PathVariable Long id) { eventRepository.deleteById(id); }
    
    @PutMapping("/event/{name1}/attend/{name2}")
    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
    public ResponseEntity<String> attendEvent(@ApiParam(value = "user username ", required = true) @PathVariable("name1") String eventname, @PathVariable("name2") String username) {
        return eventService.attendEvent(username,eventname);
    }

    @PutMapping("/event/{name1}/notattend/{name2}")
    @ApiOperation(value = "Find Users by username", notes = "Provide an username to look up specific user from userdb")
    public ResponseEntity<String> notAttendEvent(@ApiParam(value = "user username ", required = true) @PathVariable("name1") String eventname, @PathVariable("name2") String username) {
        return eventService.notAttendEvent(username,eventname);
    }
}


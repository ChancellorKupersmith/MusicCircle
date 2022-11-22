package com.service.music_circle_backend.services.event;

import com.service.music_circle_backend.entities.event.Event;
import com.service.music_circle_backend.entities.user.User;
import com.service.music_circle_backend.repos.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.service.music_circle_backend.services.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserService userService;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public ResponseEntity<String> saveEvent(Event newEvent){
        List<Event>events = eventRepository.findAll();
        for (Event event : events) {
            if (event.getName().equals(newEvent.getName()) && event.getLocation().equals(newEvent.getLocation())) {
                return new ResponseEntity<>("EVENT ALREADY EXISTS", HttpStatus.OK);
            }
        }
        eventRepository.save(newEvent);
        return new ResponseEntity<>("SUCCESSFUL EVENT REGISTRATION", HttpStatus.OK);
    }

    public Event getEventByName(String name){
        List<Event>events = eventRepository.findAll();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    public List<Event> getEventsByName(String name){
        List<Event> eventsByName = new ArrayList<Event>();
        List<Event> all = eventRepository.findAll();
        for(Event event : all){
            if(event.getName().startsWith(name)){
                eventsByName.add(event);
            }
            else if(event.getCity().startsWith(name)){
                eventsByName.add(event);
            }
            else if(event.getState().startsWith(name)){
                eventsByName.add(event);
            }
            else if(event.getZipcode().startsWith(name)){
                eventsByName.add(event);
            }
            else if(event.getStreetAddress().startsWith(name)){
                eventsByName.add(event);
            }
        }
        return eventsByName;
    }

    public Event getEventByLocation(String state){
        List<Event>events = eventRepository.findAll();
        for (Event event : events) {
            if (event.getState().equals(state)) {
                return event;
            }
        }
        return null;
    }

    public void removeEvent(String name){
        List<Event>events = eventRepository.findAll();
        for (Event event : events) {
            if (event.getName().equals(name)) {
                eventRepository.delete(event);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Event> getAll(){
        return new ArrayList<>(eventRepository.findAll());
    }

    public List<Event> getEventsByState(String state) {
        List<Event>events = eventRepository.findAll();
        List<Event>list = new ArrayList<>();
        for (Event event : events) {
            if (event.getState().equals(state)) {
                list.add(event);
            }
        }
        return list;
    }
    
    public ResponseEntity<String> attendEvent(String username, String eventname) {
        Event event = getEventByName(eventname);
        List<User> userAttendingList = event.getUsersAttending_usernames();
        User user = userService.getUser(username);
        List<Event> userAttendingEvents = user.getAttendingEvents();

        if(userAttendingList.contains(user)){
            return new ResponseEntity<>("ALREADY ATTENDING EVENT", HttpStatus.OK);
        }else{
            userAttendingList.add(userService.getUser(username));
            event.setUsersAttending(userAttendingList);
            userAttendingEvents.add(event);
            user.setAttendingEvents(userAttendingEvents);
        }
        eventRepository.save(event);
        return new ResponseEntity<>("SUCCESSFULLY ATTENDING EVENT", HttpStatus.OK);
    }

    public ResponseEntity<String> notAttendEvent(String username, String eventname) {
        Event event = getEventByName(eventname);
        List<User> userAttendingList = event.getUsersAttending_usernames();
        User user = userService.getUser(username);
        List<Event> userAttendingEvents = user.getAttendingEvents();

        if(!userAttendingList.contains(user)){
            return new ResponseEntity<>("ALREADY NOT ATTENDING EVENT", HttpStatus.OK);
        }else{
            userAttendingList.remove(user);
            event.setUsersAttending(userAttendingList);
            userAttendingEvents.remove(event);
            user.setAttendingEvents(userAttendingEvents);
        }
        eventRepository.save(event);
        return new ResponseEntity<>("SUCCESSFULLY NOT ATTENDING EVENT", HttpStatus.OK);
    }
}


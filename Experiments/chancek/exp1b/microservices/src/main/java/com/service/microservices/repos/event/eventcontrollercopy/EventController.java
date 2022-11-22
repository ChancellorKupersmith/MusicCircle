package com.service.microservices.controllers.event;

import com.service.microservices.entities.event.Event;
import com.service.microservices.exceptions.event.EventNotFoundException;
import com.service.microservices.repos.event.EventRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @GetMapping("/events")
    List<Event> all() { return eventRepository.findAll(); }
    @GetMapping("/events/{id}")
    Event one(@PathVariable Long id) { return eventRepository.findById(id).orElseThrow(); }

    //For creating new events
    @PostMapping("/events")
    Event newEvent(@RequestBody Event newEvent) { return eventRepository.save(newEvent); }
    @PostMapping("/events/{userId}/{location}/{dateTime}")
    Event newEvent(@PathVariable Long userId, @PathVariable String location, @PathVariable Date dateTime) {
        Event newEvent = new Event(userId, location, dateTime);
        return eventRepository.save(newEvent);
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
    Event replaceEventDateTime(@PathVariable Long id, @RequestParam("dateTime") Date dateTime) {
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
    @PutMapping("events/{id}/usersPerforming")
    Event replaceEventUsersPerforming(@PathVariable Long id, @RequestParam("usersPerforming") List<Long> usersPerforming) {
        return eventRepository.findById(id).map(event -> {
            event.setUsersPerforming(usersPerforming);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }
    //Updates usersAttending
    @PutMapping("events/{id}/usersAttending")
    Event replaceEventUsersAttending(@PathVariable Long id, @RequestParam("usersAttending") List<Long> usersAttending) {
        return eventRepository.findById(id).map(event -> {
            event.setUsersAttending(usersAttending);
            return eventRepository.save(event);
        }).orElseThrow(() -> new EventNotFoundException(id));
    }

    @DeleteMapping("events/{id}")
    void deleteEvent(@PathVariable Long id) { eventRepository.deleteById(id); }
}

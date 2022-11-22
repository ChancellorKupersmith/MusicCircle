package com.service.music_circle_backend.repos.event;

import com.service.music_circle_backend.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findEventByName(String name);
    List<Event> findEventsByName(String name);
}


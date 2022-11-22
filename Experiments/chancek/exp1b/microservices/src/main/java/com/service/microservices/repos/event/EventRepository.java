package com.service.microservices.repos.event;

import com.service.microservices.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

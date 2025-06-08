package com.example.reservation_des_evenements.Controller;

import com.example.reservation_des_evenements.Repositories.EventRepository;
import com.example.reservation_des_evenements.entities.Event;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {
    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping("/event")
    public Event saveEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    @PostMapping("/events")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @DeleteMapping("/student/{id}")
    public void deleteEvent(@PathVariable int id) {
        eventRepository.deleteById(id);
    }

    @PutMapping("event")
    public Event updateEvent(@RequestBody Event event) {
       return eventRepository.save(event);
    }
}

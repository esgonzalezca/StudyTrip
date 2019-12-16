package co.edu.unal.gdeback.controller;

import co.edu.unal.gdeback.exception.ResourceNotFoundException;
import co.edu.unal.gdeback.model.Event;
import co.edu.unal.gdeback.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/event")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @PostMapping("/event")
    public Event createEvent(@Valid @RequestBody Event event) {
        return eventRepository.save(event);
    }

    @GetMapping("/event/{event_id}")
    public Event getEventById(@PathVariable(value = "event_id") Integer eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", eventId));
    }
    
    @PutMapping("/event/{event_id}")
    public Event updatedEvent(@PathVariable(value = "event_id") Integer eventId,
                           @Valid @RequestBody Event eventDetails) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", eventId));

        //event.setEvent_members(eventDetails.getEvent_members());
        Event updatedEvent = eventRepository.save(event);
        return updatedEvent;
    }
    
    @DeleteMapping("/event/{event_id}")
    public ResponseEntity<?> deleteEvent(@PathVariable(value = "group_id") Integer eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "EventId", eventId));

        eventRepository.delete(event);
        return ResponseEntity.ok().build();
    }
}
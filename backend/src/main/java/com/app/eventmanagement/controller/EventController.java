package com.app.eventmanagement.controller;

import com.app.eventmanagement.Repositories.EventRepository;
import com.app.eventmanagement.model.Event;
import com.app.eventmanagement.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
    private final EventRepository eventRepository;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/All")
    public List<Event> getAllEvent() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or @eventService.isOwner(#id, authentication.name)")
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    @PreAuthorize("hasRole('ADMIN') or @eventService.isOwner(#id, authentication.name)")
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/search")
    public List<Event> searchEvent(
            @RequestParam(required = false)String location, @RequestParam(required = false) String name){
            return eventService.searchEvents(location,name);
    }
    @GetMapping
    public Page<Event> getEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "asc") String direction
    ){

        Sort.Direction dir = direction.equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        return eventRepository.findAll(PageRequest.of(page, size, Sort.by(dir, sort)));
    }
}
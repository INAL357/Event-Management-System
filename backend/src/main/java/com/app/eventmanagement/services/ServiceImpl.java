package com.app.eventmanagement.services;

import com.app.eventmanagement.Repositories.EventRepository;
import com.app.eventmanagement.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements EventService{

    private final EventRepository eventRepository;


    @Override
    public Event createEvent(Event event) {
       return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(()->new RuntimeException("Event not found"));
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Event existing = eventRepository.getById(id);

        existing.setUsername(event.getUsername());
        existing.setDescription(event.getDescription());
        existing.setEventDate(event.getEventDate());
        existing.setLocation(event.getLocation());
        existing.setPrice(event.getPrice());
        existing.setTotalTickets(event.getTotalTickets());
        existing.setAvailableTickets(event.getAvailableTickets());

        return eventRepository.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}

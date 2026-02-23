package com.app.eventmanagement.services;

import com.app.eventmanagement.model.Event;

import java.util.List;

public interface EventService {

    Event createEvent(Event event);

    List<Event> getAllEvents();

    Event getEventById(Long id);

    Event updateEvent(Long id,Event event);

    void deleteEvent(Long id);

    boolean isOwner(Long id, String username);
}

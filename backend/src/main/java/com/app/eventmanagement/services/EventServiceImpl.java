package com.app.eventmanagement.services;

import com.app.eventmanagement.Repositories.EventRepository;
import com.app.eventmanagement.Repositories.UserRepository;
import com.app.eventmanagement.model.Event;
import com.app.eventmanagement.model.Role;
import com.app.eventmanagement.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements EventService{

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private void validateTickets(Event event) {
        if (event.getAvailableTickets() < 0 ||
                event.getAvailableTickets() > event.getTotalTickets()) {
            throw new RuntimeException("Invalid ticket count");
        }
    }

    @Override
    public Event createEvent(Event event) {
        event.setAvailableTickets(event.getTotalTickets());

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setCreatedBy(user);


        validateTickets(event);
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
    public Event updateEvent(Long id, Event updatedEvent) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // update fields
        event.setName(updatedEvent.getName());
        event.setDescription(updatedEvent.getDescription());
        event.setLocation(updatedEvent.getLocation());
        event.setPrice(updatedEvent.getPrice());
        event.setTotalTickets(updatedEvent.getTotalTickets());
        event.setAvailableTickets(updatedEvent.getAvailableTickets());

        // Validation
        validateTickets(event);

        return eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long id) {
    String email = SecurityContextHolder.getContext()
                    .getAuthentication()
                            .getName();

    User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new RuntimeException("User not found"));

    Event event = eventRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Event not found"));
        eventRepository.deleteById(id);

        //  ADMIN can delete anything
        if (user.getRole() == Role.ROLE_ADMIN) {
            eventRepository.delete(event);
            return;
        }

        // ORGANIZER can delete only their own event
        if (user.getRole() == Role.ROLE_ORGANIZER &&
                event.getCreatedBy().getId().equals(user.getId())) {

            eventRepository.delete(event);
            return;
        }


        throw new RuntimeException("You are not authorized to delete this event");
    }

    public boolean isOwner(Long eventId, String username) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return event.getCreatedBy().getUsername().equals(username);
    }

}

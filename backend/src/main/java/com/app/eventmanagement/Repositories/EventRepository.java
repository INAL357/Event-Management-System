package com.app.eventmanagement.Repositories;

import com.app.eventmanagement.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByLocation(String Location);

    List<Event> findByNameContainingIgnoreCase(String name);


    // List<Event> findByEventDate(LocalDateTime eventDate);
}

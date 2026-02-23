package com.app.eventmanagement.services;

import com.app.eventmanagement.Repositories.BookingRepository;
import com.app.eventmanagement.Repositories.EventRepository;
import com.app.eventmanagement.Repositories.UserRepository;
import com.app.eventmanagement.model.Booking;
import com.app.eventmanagement.model.Event;
import com.app.eventmanagement.model.PaymentStatus;
import com.app.eventmanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Long eventId){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(()->new RuntimeException("Event not found"));

        Booking booking = Booking.builder()
                .user(user)
                .event(event)
                .amount(50L)
                .paymentStatus(PaymentStatus.PENDING)
                .build();

        return bookingRepository.save(booking);

    }
}

package com.app.eventmanagement.services;

import com.app.eventmanagement.Repositories.BookingRepository;
import com.app.eventmanagement.Repositories.EventRepository;
import com.app.eventmanagement.Repositories.UserRepository;
import com.app.eventmanagement.dto.BookingResponse;
import com.app.eventmanagement.model.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private UserRepository userRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }


    @Override
    public BookingResponse bookEvents(Long eventId, int quantity) {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() < quantity) {
            throw new RuntimeException("Not enough tickets available");
        }

        event.setAvailableTickets(event.getAvailableTickets() - quantity);
        BigDecimal amount = event.getPrice().multiply(BigDecimal.valueOf(quantity));

        Booking booking = new Booking();
        booking.setAmount(amount.longValue());
        booking.setTotalPrice(amount.longValue());
        booking.setUser(user);
        booking.setEvent(event);
        booking.setQuantity(quantity);
        booking.setBookingTime(LocalDateTime.now());
        booking.setPaymentStatus(PaymentStatus.PENDING);

        eventRepository.save(event);
        Booking saved = bookingRepository.save(booking);

        return new BookingResponse(
               saved.getId(),
                saved.getEvent().getName(),
                saved.getEvent().getLocation(),
                saved.getQuantity(),
                saved.getBookingTime(),
                user.getUsername()

        );
    }


    @Override
    public List<BookingResponse> getUserBooking() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> bookings = bookingRepository.findByUser(user);

        return bookings.stream().map(b -> new BookingResponse(
                b.getId(),
                b.getEvent().getName(),
                b.getEvent().getLocation(),
                b.getQuantity(),
                b.getBookingTime(),
                user.getUsername()
        )).toList();
    }

    //Cancel booking Implementation
    public Booking  cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Already cancelled");
        }

        //  update status
        booking.setStatus(BookingStatus.CANCELLED);

        //  restore tickets
        Event event = booking.getEvent();
        event.setAvailableTickets(
                event.getAvailableTickets() + booking.getQuantity()
        );

        eventRepository.save(event);
        return bookingRepository.save(booking);
    }

}





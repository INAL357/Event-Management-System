package com.app.eventmanagement.controller;

import com.app.eventmanagement.dto.BookingResponse;
import com.app.eventmanagement.model.Booking;
import com.app.eventmanagement.services.BookingService;
import com.app.eventmanagement.services.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/bookings")
    @RequiredArgsConstructor
    public class BookingController {

    private final BookingService bookingService;


    @PostMapping("/{eventId}")
    public BookingResponse bookEvent(@PathVariable Long eventId,
                                                        @RequestParam int quantity) {
        return bookingService.bookEvents(eventId, quantity);
    }
    @GetMapping("/my-bookings")
    public List<BookingResponse> getMyBookings() {
        return bookingService.getUserBooking();
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled");
    }


}

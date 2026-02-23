package com.app.eventmanagement.controller;

import com.app.eventmanagement.model.Booking;
import com.app.eventmanagement.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/bookings")
    @RequiredArgsConstructor
    public class BookingController {

        private final BookingService bookingService;

        @PostMapping("/{eventId}")
        public Booking createBooking(@PathVariable Long eventId) {
            return bookingService.createBooking(eventId);
        }
    }


package com.app.eventmanagement.dto;

import java.time.LocalDateTime;

public class BookingResponse {

        private Long bookingId;
        private String eventName;
        private String location;
        private int quantity;
        private LocalDateTime bookingTime;

        private String username;

        // Constructor
        public BookingResponse(Long bookingId, String eventName, String location,
                                  int quantity, LocalDateTime bookingTime, String username) {
            this.bookingId = bookingId;
            this.eventName = eventName;
            this.location = location;
            this.quantity = quantity;
            this.bookingTime = bookingTime;
            this.username = username;
        }

        // Getters
        public Long getBookingId() { return bookingId; }
        public String getEventName() { return eventName; }
        public String getLocation() { return location; }
        public int getQuantity() { return quantity; }
        public LocalDateTime getBookingTime() { return bookingTime; }
        public String getUsername() { return username; }
    }

